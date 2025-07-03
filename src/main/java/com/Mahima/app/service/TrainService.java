package com.Mahima.app.service;

import com.Mahima.app.model.TrainRoute;
import com.Mahima.app.model.TrainTicket;
import com.Mahima.app.model.User;
import com.Mahima.app.repository.TrainRouteRepository;
import com.Mahima.app.repository.TrainTicketRepository;
import com.Mahima.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.time.LocalDate;

@Service
public class TrainService {

    @Autowired
    private TrainRouteRepository trainRouteRepository;

    @Autowired
    private TrainTicketRepository trainTicketRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public TrainTicket bookTicket(String source, String destination, LocalDate date,
                                  String travelClass, int seats, Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

        Optional<TrainRoute> routeOpt = trainRouteRepository
                .findBySourceIgnoreCaseAndDestinationIgnoreCase(source, destination);

        if (routeOpt.isEmpty()) {
            return null;
        }
        TrainRoute route = routeOpt.get();

        boolean seatAvailable = switch (travelClass.toLowerCase()) {
            case "ac" -> route.getAcSeats() >= seats;
            case "sleeper" -> route.getSleeperSeats() >= seats;
            case "general" -> route.getGeneralSeats() >= seats;
            default -> false;
        };

        if (!seatAvailable) return null;

        switch (travelClass.toLowerCase()) {
            case "ac" -> route.setAcSeats(route.getAcSeats() - seats);
            case "sleeper" -> route.setSleeperSeats(route.getSleeperSeats() - seats);
            case "general" -> route.setGeneralSeats(route.getGeneralSeats() - seats);
        }
        trainRouteRepository.save(route);

        TrainTicket ticket = new TrainTicket();
        ticket.setUser(user);
        ticket.setArrivalStation(destination);
        ticket.setDepartureStation(source);
        ticket.setDepartureDate(date);
        ticket.setTravelClass(travelClass);
        ticket.setSeats(seats);
        ticket.setTrainNumber(route.getTrainNumber());
        ticket.setTrainName(route.getTrainName());
        ticket.setPrice(calculatePrice(travelClass, seats));
        ticket.setStatus("CONFIRMED");

        return trainTicketRepository.save(ticket);
    }

    @Transactional
    public boolean cancelTrainTicket(Long ticketId) {
        Optional<TrainTicket> ticketOpt = trainTicketRepository.findById(ticketId);
        if (ticketOpt.isPresent()) {
            TrainTicket ticket = ticketOpt.get();
            Optional<TrainRoute> routeOpt = trainRouteRepository.findByTrainNumber(ticket.getTrainNumber());
            if (routeOpt.isPresent()) {
                TrainRoute route = routeOpt.get();
                switch (ticket.getTravelClass().toLowerCase()) {
                    case "ac" -> route.setAcSeats(route.getAcSeats() + ticket.getSeats());
                    case "sleeper" -> route.setSleeperSeats(route.getSleeperSeats() + ticket.getSeats());
                    case "general" -> route.setGeneralSeats(route.getGeneralSeats() + ticket.getSeats());
                }
                trainRouteRepository.save(route);
            }
            trainTicketRepository.deleteById(ticketId);
            return true;
        }
        return false;
    }

    public List<TrainTicket> findByUser(User user) {
        return trainTicketRepository.findByUser(user);
    }

    public TrainTicket getTicketById(Long ticketId) {
        return trainTicketRepository.findById(ticketId).orElse(null);
    }
    public void bookTrainTicket(TrainTicket ticket) {
        LocalDate today = LocalDate.now();
        
        if (ticket.getDepartureDate().isBefore(today)) {
            throw new IllegalArgumentException("Travel date cannot be in the past!");
        }
    }
    private double calculatePrice(String travelClass, int seats) {
        return switch (travelClass.toLowerCase()) {
            case "ac" -> 1200.0 * seats;
            case "sleeper" -> 800.0 * seats;
            case "general" -> 500.0 * seats;
            default -> 0.0;
        };
    }
    
}
