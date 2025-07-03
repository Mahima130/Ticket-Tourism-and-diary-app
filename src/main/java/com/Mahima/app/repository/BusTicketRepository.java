package com.Mahima.app.repository;

import com.Mahima.app.model.BusTicket;
import com.Mahima.app.model.User; // ⭐ NEW IMPORT ⭐
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusTicketRepository extends JpaRepository<BusTicket, Long> {
    // ⭐ CHANGE START ⭐
    // Old: List<BusTicket> findByPassengerName(String passengerName);
    List<BusTicket> findByUser(User user); // Find tickets by the User object
    // Or if you prefer to pass ID directly: List<BusTicket> findByUserId(Long userId);
    // ⭐ CHANGE END ⭐
}