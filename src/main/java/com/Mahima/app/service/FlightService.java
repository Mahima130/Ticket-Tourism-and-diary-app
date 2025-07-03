package com.Mahima.app.service;

import com.Mahima.app.model.FlightRoute;
import com.Mahima.app.model.FlightTicket;
import com.Mahima.app.model.User; // ⭐ NEW IMPORT ⭐
import com.Mahima.app.repository.FlightRouteRepository;
import com.Mahima.app.repository.FlightTicketRepository;
import com.Mahima.app.repository.UserRepository; // ⭐ NEW IMPORT ⭐
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class FlightService {

    @Autowired
    private FlightRouteRepository flightRouteRepository;

    @Autowired
    private FlightTicketRepository flightTicketRepository;

    @Autowired // ⭐ NEW: Inject UserRepository to find User for booking ⭐
    private UserRepository userRepository;

    @Transactional
    public Optional<FlightTicket> bookTicket(String source, String destination, LocalDate travelDate,
                                             String travelClass, int seats, Long userId) { // ⭐ CHANGED: userId instead of username ⭐

        User user = userRepository.findById(userId) // ⭐ NEW: Get User object ⭐
                    .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

        Optional<FlightRoute> routeOpt =
                flightRouteRepository.findBySourceIgnoreCaseAndDestinationIgnoreCase(source, destination);

        if (routeOpt.isEmpty()) {
            return Optional.empty();
        }

        FlightRoute route = routeOpt.get();

        boolean available = false;
        switch (travelClass.toLowerCase()) {
            case "economy" -> {
                if (route.getEconomySeats() >= seats) {
                    route.setEconomySeats(route.getEconomySeats() - seats);
                    available = true;
                }
            }
            case "business" -> {
                if (route.getBusinessSeats() >= seats) {
                    route.setBusinessSeats(route.getBusinessSeats() - seats);
                    available = true;
                }
            }
            case "first class" -> {
                if (route.getFirstClassSeats() >= seats) {
                    route.setFirstClassSeats(route.getFirstClassSeats() - seats);
                    available = true;
                }
            }
        }

        if (!available) return Optional.empty();

        flightRouteRepository.save(route);

        FlightTicket ticket = new FlightTicket();
        ticket.setUser(user); // ⭐ SET THE USER OBJECT ⭐
        ticket.setFlightNumber(route.getFlightNumber());
        ticket.setFlightName(route.getFlightName());
        ticket.setSource(source);
        ticket.setDestination(destination);
        ticket.setTravelDate(travelDate);
        ticket.setTravelClass(travelClass);
        ticket.setSeats(seats);
        // REMOVE: ticket.setUsername(username); // Remove this line
        ticket.setPrice(calculatePrice(travelClass, seats));
        ticket.setStatus("CONFIRMED"); // Set status on creation

        return Optional.of(flightTicketRepository.save(ticket));
    }

    @Transactional
    public boolean cancelFlightTicket(Long ticketId) {
        Optional<FlightTicket> ticketOpt = flightTicketRepository.findById(ticketId);
        if (ticketOpt.isPresent()) {
            FlightTicket ticket = ticketOpt.get();

            Optional<FlightRoute> routeOpt = flightRouteRepository.findByFlightNumber(ticket.getFlightNumber());
            if (routeOpt.isPresent()) {
                FlightRoute route = routeOpt.get();
                switch (ticket.getTravelClass().toLowerCase()) {
                    case "economy" -> route.setEconomySeats(route.getEconomySeats() + ticket.getSeats());
                    case "business" -> route.setBusinessSeats(route.getBusinessSeats() + ticket.getSeats());
                    case "first class" -> route.setFirstClassSeats(route.getFirstClassSeats() + ticket.getSeats());
                }
                flightRouteRepository.save(route);
            }

            flightTicketRepository.deleteById(ticketId);
            return true;
        }
        return false;
    }

    /**
     * Finds all flight tickets associated with a given User.
     * @param user The User object.
     * @return A list of FlightTicket objects for the specified user.
     */
    // ⭐ CHANGE START ⭐
    // Old: public List<FlightTicket> findByUsername(String username) {
    // Old:     return flightTicketRepository.findByUsername(username);
    // Old: }
    public List<FlightTicket> findByUser(User user) {
        return flightTicketRepository.findByUser(user);
    }
    // ⭐ CHANGE END ⭐

    private double calculatePrice(String travelClass, int seats) {
        double pricePerSeat = switch (travelClass.toLowerCase()) {
            case "business" -> 4000;
            case "first class" -> 6000;
            default -> 2500;
        };
        return pricePerSeat * seats;
    }
}