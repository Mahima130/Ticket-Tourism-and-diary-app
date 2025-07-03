package com.Mahima.app.repository;

import com.Mahima.app.model.TrainTicket;
import com.Mahima.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrainTicketRepository extends JpaRepository<TrainTicket, Long> {
    
    List<TrainTicket> findByUser(User user);
    
    List<TrainTicket> findByDepartureStationIgnoreCaseAndArrivalStationIgnoreCase(String departureStation, String arrivalStation);
}
