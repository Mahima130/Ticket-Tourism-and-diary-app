package com.Mahima.app.repository;

import com.Mahima.app.model.BusRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List; // ⭐ CHANGE/CONFIRM THIS IMPORT IS List ⭐
import java.util.Optional; // Keep this if you have other methods like findById, findByBusNumber returning Optional

@Repository
public interface BusRouteRepository extends JpaRepository<BusRoute, Long> {

    // ⭐ CRUCIAL CHANGE: Make this return List<BusRoute> ⭐
    List<BusRoute> findByDepartureCityIgnoreCaseAndArrivalCityIgnoreCase(String departure, String arrival);

    // Keep this if you have it, as finding by a unique identifier like busNumber often returns Optional
    Optional<BusRoute> findByBusNumber(String busNumber);
}