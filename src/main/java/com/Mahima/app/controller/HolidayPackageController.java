package com.Mahima.app.controller;

import com.Mahima.app.model.HolidayPackage;
import com.Mahima.app.model.HolidayPackageBooking;
import com.Mahima.app.model.User;
import com.Mahima.app.repository.UserRepository;
import com.Mahima.app.service.HolidayPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/packages") // Keep this for your other /packages related methods
public class HolidayPackageController {

    @Autowired
    private HolidayPackageService holidayPackageService;

    @Autowired
    private UserRepository userRepository; // Still needed for cancel method to get userId

    @GetMapping // Maps to /packages
    public String listPackages(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        model.addAttribute("username", principal.getName());
        List<HolidayPackage> packages = holidayPackageService.getAllPackages();
        model.addAttribute("packages", packages);
        return "packages";
    }

    // ⭐ CORRECT: The old confirmPackageBooking method has been removed/commented out from here. ⭐
    /*
    @RequestMapping(value = "/packages-confirmation", method = RequestMethod.POST)
    public String confirmPackageBooking(...) {
        // ... this code moves to PageController
    }
    */

    @PostMapping("/cancel/{bookingId}") // Maps to /packages/cancel/{bookingId}
    public String cancelPackage(@PathVariable Long bookingId, Principal principal, RedirectAttributes redirectAttributes) {
        if (principal == null) {
            return "redirect:/login";
        }
        String username = principal.getName();
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found for email: " + username));

        // Assuming your cancelPackageBooking method in service uses the bookingId to find and update status.
        // It might also need the user.getId() for security checks, depending on its implementation.
        boolean cancelled = holidayPackageService.cancelPackageBooking(bookingId);
        if (cancelled) {
            redirectAttributes.addFlashAttribute("successMessage", "Holiday package booking cancelled successfully."); // Changed to successMessage for consistency with profile.html
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to cancel the booking. It might not exist or belong to you."); // Changed to errorMessage
        }
        return "redirect:/profile";
    }
    
    
    @PostMapping("/packages-confirmation") // ⭐ THIS IS THE CRUCIAL MAPPING ⭐
    public String confirmPackageBooking(@RequestParam String packageName,
                                        @RequestParam double price,
                                        Principal principal,
                                        Model model,
                                        RedirectAttributes redirectAttributes) {

        // 1. Authentication Check
        if (principal == null) {
            redirectAttributes.addFlashAttribute("error", "You must be logged in to book a package.");
            return "redirect:/login";
        }

        // 2. Get User ID from Principal
        String username = principal.getName();
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found for email: " + username));
        Long userId = user.getId();

        // 3. Perform the Booking via Service Layer
        HolidayPackageBooking booking;
        try {
            booking = holidayPackageService.bookPackage(userId, packageName, price);
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to book package: " + e.getMessage()); // Changed to errorMessage
            return "redirect:/packages";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "An unexpected error occurred during booking."); // Changed to errorMessage
            return "redirect:/packages";
        }

        // 4. Add Booking Details to Model and Return Confirmation View
        model.addAttribute("booking", booking);
        return "packages-confirmation"; // This correctly points to your HTML template
    }
} 