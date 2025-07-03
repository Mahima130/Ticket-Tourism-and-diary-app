package com.Mahima.app.service;

import com.Mahima.app.model.BusRoute;
import com.Mahima.app.repository.BusRouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List; // This import is already there

@Service
public class BusRouteService {

    @Autowired
    private BusRouteRepository busRouteRepository;

    public List<BusRoute> findRoutes(String departure, String arrival) {
        // This line will now correctly return a List<BusRoute>
        return busRouteRepository.findByDepartureCityIgnoreCaseAndArrivalCityIgnoreCase(departure, arrival);
    }

    @Transactional
    public void reduceSeats(BusRoute route, int bookedSeats) {
        route.setTotalSeats(route.getTotalSeats() - bookedSeats);
        busRouteRepository.save(route);
    }
}