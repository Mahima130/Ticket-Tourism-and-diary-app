package com.Mahima.app.controller;

import com.Mahima.app.model.User;
// Corrected Model Imports based on image_0fbccc.png
import com.Mahima.app.model.BusTicket;
import com.Mahima.app.model.FlightTicket;
import com.Mahima.app.model.TrainTicket;
import com.Mahima.app.model.HolidayPackageBooking;

// Corrected Service Imports based on image_0fbcab.png
import com.Mahima.app.service.UserService;
import com.Mahima.app.service.BusTicketService;
import com.Mahima.app.service.FlightService;
import com.Mahima.app.service.TrainService;
import com.Mahima.app.service.HolidayPackageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    // Autowire your ticket and package booking services with corrected names
    // @Autowired(required = false) is used as a safety net if a service bean isn't found
    @Autowired(required = false)
    private TrainService trainService;
    @Autowired(required = false)
    private BusTicketService busTicketService;
    @Autowired(required = false)
    private FlightService flightService;
    @Autowired(required = false)
    private HolidayPackageService holidayPackageService;


    /**
     * Handles the root URL "/"
     * Now redirects to /welcome, as /welcome.html is your desired landing page with booking cards.
     */
    @GetMapping("/")
    public String index(Principal principal, Model model) {
        if (principal != null) {
            return "redirect:/welcome";
        }
        return "index"; // For non-logged-in users (e.g., your general landing page)
    }

    /**
     * Handles the /welcome URL.
     * This method fetches all data required by the welcome.html content.
     */
    @GetMapping("/welcome")
    public String welcome(Principal principal, Model model) {
        // For guests/non-logged-in users
        if (principal == null) {
            model.addAttribute("username", "Guest");
            // Add empty booking lists for guest view
            model.addAttribute("trainTickets", Collections.emptyList());
            model.addAttribute("busTickets", Collections.emptyList());
            model.addAttribute("flightTickets", Collections.emptyList());
            model.addAttribute("packageBookings", Collections.emptyList());
            return "welcome";
        }

        // For logged-in users (existing logic)
        String userEmail = principal.getName();
        Optional<User> optionalUser = userService.findByEmail(userEmail);

        if (optionalUser.isPresent()) {
            User loggedInUser = optionalUser.get();
            model.addAttribute("username", loggedInUser.getName());
            
            // Fetch bookings
            model.addAttribute("trainTickets", trainService != null ? trainService.findByUser(loggedInUser) : Collections.emptyList());
            model.addAttribute("busTickets", busTicketService != null ? busTicketService.findByUser(loggedInUser) : Collections.emptyList());
            model.addAttribute("flightTickets", flightService != null ? flightService.findByUser(loggedInUser) : Collections.emptyList());
            model.addAttribute("packageBookings", holidayPackageService != null ? holidayPackageService.findByUser(loggedInUser) : Collections.emptyList());
        } else {
            return "redirect:/login";
        }
        return "welcome";
    }
}