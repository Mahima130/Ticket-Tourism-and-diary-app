package com.Mahima.app.controller;

import com.Mahima.app.model.User;
import com.Mahima.app.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.Collections;
import java.util.Optional;

@Controller
public class ProfileController {

    @Autowired
    private UserService userService;

    @Autowired(required = false)
    private TrainService trainService;
    
    @Autowired(required = false)
    private BusTicketService busTicketService;
    
    @Autowired(required = false)
    private FlightService flightService;
    
    @Autowired(required = false)
    private HolidayPackageService holidayPackageService;

    @GetMapping("/profile")
    public String profile(Principal principal, Model model) {
        if (principal == null) {
            return "redirect:/login";
        }

        String userEmail = principal.getName();
        Optional<User> optionalUser = userService.findByEmail(userEmail);

        if (optionalUser.isPresent()) {
            User loggedInUser = optionalUser.get();
            model.addAttribute("username", loggedInUser.getName());
            
            // Fetch bookings
            model.addAttribute("trainTickets", trainService != null ? 
                trainService.findByUser(loggedInUser) : Collections.emptyList());
            model.addAttribute("busTickets", busTicketService != null ? 
                busTicketService.findByUser(loggedInUser) : Collections.emptyList());
            model.addAttribute("flightTickets", flightService != null ? 
                flightService.findByUser(loggedInUser) : Collections.emptyList());
            model.addAttribute("packageBookings", holidayPackageService != null ? 
                holidayPackageService.findByUser(loggedInUser) : Collections.emptyList());
        } else {
            return "redirect:/login";
        }
        return "profile";
    }
}