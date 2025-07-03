package com.Mahima.app.controller;

import com.Mahima.app.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class TicketBookingController {

    @Autowired
    private HolidayPackageService holidayPackageService;
    @Autowired
    private FlightService flightService;
    @Autowired
    private TrainService trainService;
    @Autowired
    private BusTicketService busTicketService;

    @PostMapping("/cancel-package/{id}")
    public String cancelHolidayPackage(@PathVariable Long id, 
                                     RedirectAttributes redirectAttributes) {
        try {
            holidayPackageService.cancelPackageBooking(id);
            redirectAttributes.addFlashAttribute("success", "Package booking cancelled successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to cancel package booking");
        }
        return "redirect:/profile";
    }

    @PostMapping("/cancel-flight/{id}")
    public String cancelFlight(@PathVariable Long id, 
                             RedirectAttributes redirectAttributes) {
        try {
            flightService.cancelFlightTicket(id);
            redirectAttributes.addFlashAttribute("success", "Flight booking cancelled successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to cancel flight booking");
        }
        return "redirect:/profile";
    }

    @PostMapping("/cancel-train/{id}")
    public String cancelTrain(@PathVariable Long id, 
                            RedirectAttributes redirectAttributes) {
        try {
            trainService.cancelTrainTicket(id);
            redirectAttributes.addFlashAttribute("success", "Train booking cancelled successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to cancel train booking");
        }
        return "redirect:/profile";
    }

    @PostMapping("/cancel-bus/{id}")
    public String cancelBus(@PathVariable Long id, 
                          RedirectAttributes redirectAttributes) {
        try {
            busTicketService.cancelBusTicket(id);
            redirectAttributes.addFlashAttribute("success", "Bus booking cancelled successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to cancel bus booking");
        }
        return "redirect:/profile";
    }
}