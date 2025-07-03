package com.Mahima.app.repository;

import com.Mahima.app.model.HolidayPackageBooking;
import com.Mahima.app.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HolidayPackageBookingRepository extends JpaRepository<HolidayPackageBooking, Long> {
    // This method is correctly defined to find bookings by the userId field.
    List<HolidayPackageBooking> findByUserId(Long userId);
    
    List<HolidayPackageBooking> findByUser(User user);
}