package com.Mahima.app.controller;

import com.Mahima.app.model.FlightRoute;
import com.Mahima.app.model.FlightTicket;
import com.Mahima.app.model.User;
import com.Mahima.app.repository.FlightRouteRepository;
import com.Mahima.app.service.FlightService;
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
public class FlightBookingController {

    @Autowired
    private FlightService flightService;

    @Autowired
    private UserService userService;

    @Autowired
    private FlightRouteRepository flightRouteRepository;

    @GetMapping("/flight")
    public String showFlightBookingForm(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        model.addAttribute("username", principal.getName());
        return "flight";
    }

    @PostMapping("/ticket/book/flight")
    public String bookFlightTicket(
            @RequestParam String source,
            @RequestParam String destination,
            @RequestParam("travelDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam String travelClass,
            @RequestParam int seats,
            Principal principal,
            RedirectAttributes redirectAttributes) {

        if (principal == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "You must be logged in to book a flight ticket.");
            return "redirect:/login";
        }

        String userEmail = principal.getName();
        Optional<User> optionalUser = userService.findByEmail(userEmail);

        if (optionalUser.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "User not found. Please log in again.");
            return "redirect:/flight";
        }

        User loggedInUser = optionalUser.get();
        FlightTicket ticket;

        try {
            ticket = flightService.bookTicket(source, destination, date, travelClass, seats, loggedInUser.getId())
                    .orElseThrow(() -> new IllegalArgumentException("No matching flight or insufficient seats available."));
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Booking failed: " + e.getMessage());
            return "redirect:/flight";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "An unexpected error occurred. Please try again.");
            return "redirect:/flight";
        }

        redirectAttributes.addFlashAttribute("ticket", ticket);
        return "redirect:/flight/confirmation";
    }

    @GetMapping("/flight/confirmation")
    public String showConfirmationPage(Model model) {
        if (!model.containsAttribute("ticket")) {
            return "redirect:/flight";
        }
        return "flight-confirmation";
    }

    @GetMapping("/ticket/book/flight/api/routes")
    @ResponseBody
    public List<FlightRoute> getFlightRoutes() {
        return flightRouteRepository.findAll();
    }

    @PostMapping("/ticket/book/flight/cancel/{ticketId}")
    public String cancelFlightTicket(@PathVariable Long ticketId, RedirectAttributes redirectAttributes) {
        boolean cancelled = flightService.cancelFlightTicket(ticketId);
        if (cancelled) {
            redirectAttributes.addFlashAttribute("successMessage", "Flight ticket cancelled successfully!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to cancel flight ticket.");
        }
        return "redirect:/profile";
    }
}
