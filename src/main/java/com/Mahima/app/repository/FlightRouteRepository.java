package com.Mahima.app.repository;

import com.Mahima.app.model.FlightRoute;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FlightRouteRepository extends JpaRepository<FlightRoute, Long> {
    Optional<FlightRoute> findBySourceIgnoreCaseAndDestinationIgnoreCase(String source, String destination);
    Optional<FlightRoute> findByFlightNumber(String flightNumber);  // ✅ returns Optional to match your service code
}
