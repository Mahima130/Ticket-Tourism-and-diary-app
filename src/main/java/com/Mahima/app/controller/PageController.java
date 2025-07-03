package com.Mahima.app.controller;

import com.Mahima.app.model.HolidayPackageBooking;
import com.Mahima.app.model.User;
import com.Mahima.app.repository.UserRepository;
import com.Mahima.app.service.HolidayPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller // This controller handles general page mappings
public class PageController {

    @Autowired
    private HolidayPackageService holidayPackageService;

    @Autowired
    private UserRepository userRepository;

    // ... (any other general page mappings you have in PageController) ...

    // This method handles the POST request from the "Book" button specifically
    // It's mapped directly to /packages-confirmation
    @PostMapping("/packages-confirmation") // Absolute path, no ambiguity from other @RequestMapping
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
            redirectAttributes.addFlashAttribute("error", "Failed to book package: " + e.getMessage());
            return "redirect:/packages"; // Redirect back to /packages to show packages list
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "An unexpected error occurred during booking.");
            return "redirect:/packages"; // Redirect back to /packages
        }

        // 4. Add Booking Details to Model and Return Confirmation View
        model.addAttribute("booking", booking);
        // ⭐ IMPORTANT: This returns the correct template name
        return "packages-confirmation";
    }

//    @GetMapping("/") // Maps to the root URL (e.g., http://localhost:8080/)
//    public String home() {
//        return "welcome"; // This will look for src/main/resources/templates/welcome.html
//    }

//    @GetMapping("/diary") // Maps to /diary (e.g., http://localhost:8080/diary)
//    public String diary() {
//        return "diary"; // This will look for src/main/resources/templates/diary.html
//    }

    // ⭐ REMOVE THIS MAPPING FROM PageController TO AVOID CONFLICT ⭐
    // @GetMapping("/packages")
    // public String packages() {
    //     return "packages";
    // }
}