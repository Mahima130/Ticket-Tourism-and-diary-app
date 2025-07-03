package com.Mahima.app.service;

import com.Mahima.app.model.BusRoute;
import com.Mahima.app.model.BusTicket;
import com.Mahima.app.model.User;
import com.Mahima.app.repository.BusRouteRepository;
import com.Mahima.app.repository.BusTicketRepository;
import com.Mahima.app.repository.UserRepository;

import java.time.LocalDate;
import java.util.List; // ⭐ ENSURE THIS IS IMPORTED ⭐
// import java.util.Optional; // ⭐ REMOVE THIS IMPORT if Optional is no longer used for routes search here ⭐
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BusTicketService {

    @Autowired
    private BusTicketRepository busTicketRepository;

    @Autowired
    private BusRouteRepository busRouteRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public BusTicket saveTicket(BusTicket ticket) {
        return busTicketRepository.save(ticket);
    }

    @Transactional
    public BusTicket bookBusTicket(String source, String destination, LocalDate travelDate,
                                   int seats, Long userId) {
        // 1. Get the User object
        User user = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

        // ⭐ CHANGE START: Expecting List<BusRoute> now ⭐
        List<BusRoute> routes = busRouteRepository.findByDepartureCityIgnoreCaseAndArrivalCityIgnoreCase(source, destination);
        if (routes.isEmpty()) { // Check if the list is empty
            throw new IllegalArgumentException("Bus route not found from " + source + " to " + destination);
        }
        // Assuming you want to book on the first available route if multiple exist.
        // You might need more complex logic here if users can choose specific routes.
        BusRoute route = routes.get(0); // Get the first route from the list
        // ⭐ CHANGE END ⭐

        // Assume seat availability check and deduction logic here
        // This logic remains the same
        if (route.getTotalSeats() < seats) { // Add a check for seat availability before deducting
            throw new IllegalArgumentException("Not enough seats available on the selected route.");
        }
        route.setTotalSeats(route.getTotalSeats() - seats);
        busRouteRepository.save(route);

        BusTicket ticket = new BusTicket();
        ticket.setUser(user);
        ticket.setBusNumber(route.getBusNumber());
        ticket.setDepartureLocation(source);
        ticket.setArrivalLocation(destination);
        ticket.setTravelDate(travelDate);
        ticket.setNumberOfSeats(seats);
        ticket.setPrice(100.0 * seats); // Example price calculation
        ticket.setStatus("CONFIRMED");

        return busTicketRepository.save(ticket);
    }

    @Transactional
    public boolean cancelBusTicket(Long ticketId) {
        Optional<BusTicket> ticketOpt = busTicketRepository.findById(ticketId);
        if (ticketOpt.isPresent()) {
            BusTicket ticket = ticketOpt.get();

            Optional<BusRoute> routeOpt = busRouteRepository.findByBusNumber(ticket.getBusNumber());
            if (routeOpt.isPresent()) {
                BusRoute route = routeOpt.get();
                route.setTotalSeats(route.getTotalSeats() + ticket.getNumberOfSeats());
                busRouteRepository.save(route);
            }
            busTicketRepository.deleteById(ticketId);
            return true;
        }
        return false;
    }

    /**
     * Finds all bus tickets associated with a given User.
     * @param user The User object.
     * @return A list of BusTicket objects for the specified user.
     */
    public List<BusTicket> findByUser(User user) {
        return busTicketRepository.findByUser(user);
    }
}