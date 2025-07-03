package com.Mahima.app.controller;

import com.Mahima.app.model.BusRoute;
import com.Mahima.app.model.BusTicket;
import com.Mahima.app.model.User;
import com.Mahima.app.repository.BusRouteRepository;
import com.Mahima.app.service.BusTicketService;
import com.Mahima.app.service.BusRouteService;
import com.Mahima.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/ticket/book/bus")
public class BusBookingController {

    @Autowired
    private BusTicketService busTicketService;

    @Autowired
    private BusRouteService busRouteService;

    @Autowired
    private UserService userService;
    
    @Autowired
    private BusRouteRepository busRouteRepository; // Changed from busRouteRepo to busRouteRepository for consistency

    @GetMapping
    public String showBusBookingForm(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        model.addAttribute("username", principal.getName());
        return "bus";
    }

    @PostMapping
    public String bookBusTicket(@RequestParam String source,
                              @RequestParam String destination,
                              @RequestParam("travelDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                              @RequestParam int seats,
                              Principal principal,
                              Model model,
                              RedirectAttributes redirectAttributes) {

        if (principal == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "You must be logged in to book a bus ticket.");
            return "redirect:/login";
        }

        String userEmail = principal.getName();
        Optional<User> optionalUser = userService.findByEmail(userEmail);

        if (optionalUser.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "User not found. Please log in again.");
            return "redirect:/ticket/book/bus";
        }
        User loggedInUser = optionalUser.get();

        BusTicket ticket = null;
        try {
            ticket = busTicketService.bookBusTicket(source, destination, date, seats, loggedInUser.getId());
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Booking failed: " + e.getMessage());
            return "redirect:/ticket/book/bus";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "An unexpected error occurred during booking.");
            return "redirect:/ticket/book/bus";
        }

        if (ticket == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "No matching bus route or insufficient seats available.");
            return "redirect:/ticket/book/bus";
        }

        model.addAttribute("ticket", ticket);
        model.addAttribute("successMessage", "Bus ticket booked successfully!");
        return "bus-confirmation";
    }

    @GetMapping("/api/bus/routes")
    @ResponseBody
    @CrossOrigin(origins = "*") // Add this if your frontend is on a different port
    public List<BusRoute> getRoutes() {
        return busRouteRepository.findAll();
    }
}

@Controller
@RequestMapping("/bus")
class BusBookingShortcutController {
    
    @GetMapping
    public String redirectToBusBooking() {
        return "redirect:/ticket/book/bus";
    }
}