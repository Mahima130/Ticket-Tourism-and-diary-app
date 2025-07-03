package com.Mahima.app.controller;

import com.Mahima.app.model.TrainRoute;
import com.Mahima.app.model.TrainTicket;
import com.Mahima.app.model.User;
import com.Mahima.app.repository.TrainRouteRepository;
import com.Mahima.app.service.TrainService;
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
public class TrainBookingController {

    @Autowired
    private TrainService trainService;

    @Autowired
    private UserService userService;
    @Autowired
    private TrainRouteRepository trainRouteRepository;


    // 🟢 This will handle /train and /ticket/book/train GET
    @GetMapping({"/train", "/ticket/book/train"})
    public String showTrainBookingForm(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        model.addAttribute("username", principal.getName());
        return "train";
    }

//    @PostMapping("/ticket/book/train")
//    public String bookTrainTicket(
//            @RequestParam String source,
//            @RequestParam String destination,
//            @RequestParam("travelDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
//            @RequestParam String travelClass,
//            @RequestParam int seats,
//            Principal principal,
//            RedirectAttributes redirectAttributes) {
//
//        if (principal == null) {
//            redirectAttributes.addFlashAttribute("errorMessage", "You must be logged in to book a train ticket.");
//            return "redirect:/login";
//        }
//
//        String userEmail = principal.getName();
//        Optional<User> optionalUser = userService.findByEmail(userEmail);
//
//        if (optionalUser.isEmpty()) {
//            redirectAttributes.addFlashAttribute("errorMessage", "User not found. Please log in again.");
//            return "redirect:/ticket/book/train";
//        }
//
//        User loggedInUser = optionalUser.get();
//
//        TrainTicket ticket = trainService.bookTicket(
//                source,
//                destination,
//                date,
//                travelClass,
//                seats,
//                loggedInUser.getId()
//        );
//
//        if (ticket == null) {
//            redirectAttributes.addFlashAttribute("errorMessage", "No matching train or insufficient seats available.");
//            return "redirect:/ticket/book/train";
//        }
//
//        redirectAttributes.addFlashAttribute("successMessage", "Train ticket booked successfully!");
//        return "redirect:/ticket/book/train/confirmation/" + ticket.getId();
//    }
    @PostMapping("/ticket/book/train")
    public String bookTrainTicket(
            @RequestParam String source,
            @RequestParam String destination,
            @RequestParam("travelDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
            @RequestParam String travelClass,
            @RequestParam int seats,
            Principal principal,
            RedirectAttributes redirectAttributes) {

        if (principal == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "You must be logged in to book a train ticket.");
            return "redirect:/login";
        }

        if (date.isBefore(LocalDate.now())) {
            redirectAttributes.addFlashAttribute("errorMessage", "Travel date cannot be in the past.");
            return "redirect:/ticket/book/train";
        }

        String userEmail = principal.getName();
        Optional<User> optionalUser = userService.findByEmail(userEmail);

        if (optionalUser.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "User not found. Please log in again.");
            return "redirect:/ticket/book/train";
        }

        User loggedInUser = optionalUser.get();

        TrainTicket ticket = trainService.bookTicket(
                source,
                destination,
                date,
                travelClass,
                seats,
                loggedInUser.getId()
        );

        if (ticket == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "No matching train or insufficient seats available.");
            return "redirect:/ticket/book/train";
        }

        redirectAttributes.addFlashAttribute("successMessage", "Train ticket booked successfully!");
        return "redirect:/ticket/book/train/confirmation/" + ticket.getId();
    }


    @GetMapping("/ticket/book/train/confirmation/{ticketId}")
    public String showConfirmation(@PathVariable Long ticketId, Model model) {
        TrainTicket ticket = trainService.getTicketById(ticketId);
        if (ticket == null) {
            return "redirect:/ticket/book/train";
        }
        model.addAttribute("ticket", ticket);
        return "train-confirmation";
    }
    @GetMapping("/api/train/routes")
    @ResponseBody
    public List<TrainRoute> getRoutes() {
        return trainRouteRepository.findAll();
    }


    @PostMapping("/ticket/book/train/cancel/{ticketId}")
    public String cancelTrainTicket(@PathVariable Long ticketId, Principal principal, RedirectAttributes redirectAttributes) {
        if (principal == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "You must be logged in to cancel tickets.");
            return "redirect:/login";
        }

        boolean cancelled = trainService.cancelTrainTicket(ticketId);
        if (cancelled) {
            redirectAttributes.addFlashAttribute("successMessage", "Train ticket cancelled successfully!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to cancel train ticket. It might not exist or is already cancelled.");
        }
        return "redirect:/profile";
    }
}
