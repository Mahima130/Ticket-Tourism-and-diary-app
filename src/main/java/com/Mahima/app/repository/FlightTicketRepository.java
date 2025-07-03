package com.Mahima.app.repository;

import com.Mahima.app.model.FlightTicket;
import com.Mahima.app.model.User; // ⭐ NEW IMPORT ⭐
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightTicketRepository extends JpaRepository<FlightTicket, Long> {
    // ⭐ CHANGE START ⭐
    // Old: List<FlightTicket> findByUsername(String username);
    List<FlightTicket> findByUser(User user); // Find tickets by the User object
    // Or if you prefer to pass ID directly: List<FlightTicket> findByUserId(Long userId);
    // ⭐ CHANGE END ⭐
}