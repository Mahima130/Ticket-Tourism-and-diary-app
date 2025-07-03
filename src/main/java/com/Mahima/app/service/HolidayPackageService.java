package com.Mahima.app.service;

import com.Mahima.app.model.HolidayPackage;
import com.Mahima.app.model.HolidayPackageBooking;
import com.Mahima.app.model.User;
import com.Mahima.app.repository.HolidayPackageBookingRepository;
import com.Mahima.app.repository.HolidayPackageRepository;
import com.Mahima.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class HolidayPackageService {

    @Autowired
    private HolidayPackageRepository packageRepo;

    @Autowired
    private HolidayPackageBookingRepository bookingRepo;

    @Autowired
    private UserRepository userRepository;

    public List<HolidayPackage> getAllPackages() {
        return packageRepo.findAll();
    }

    @Transactional
    public HolidayPackageBooking bookPackage(Long userId, String packageName, double price) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

        HolidayPackage pkg = packageRepo.findByName(packageName)
                .orElseThrow(() -> new IllegalArgumentException("Package not found: " + packageName));

        if (pkg.getPrice() != price) {
            System.err.println("Warning: Mismatched price for package " + packageName + 
                             ". Expected: " + pkg.getPrice() + ", Received: " + price);
        }

        HolidayPackageBooking booking = new HolidayPackageBooking();
        booking.setUser(user);
        booking.setPackageName(pkg.getName());
        booking.setPrice(pkg.getPrice());
        booking.setBookingDate(LocalDateTime.now());
        booking.setStatus("CONFIRMED");

        return bookingRepo.save(booking);
    }

    @Transactional
    public boolean cancelPackageBooking(Long bookingId) {
        Optional<HolidayPackageBooking> bookingOpt = bookingRepo.findById(bookingId);
        if (bookingOpt.isPresent()) {
            HolidayPackageBooking booking = bookingOpt.get();
            
            // Check if booking can be cancelled (not already cancelled)
            if ("CANCELLED".equals(booking.getStatus())) {
                throw new IllegalStateException("Booking is already cancelled");
            }
            
            // Update status instead of deleting
            booking.setStatus("CANCELLED");
            bookingRepo.save(booking);
            return true;
        }
        return false;
    }

    public List<HolidayPackageBooking> findByUser(User user) {
        return bookingRepo.findByUser(user);
    }

    // Additional method if you need to check cancellation eligibility
    public boolean isCancellable(Long bookingId) {
        return bookingRepo.findById(bookingId)
                .map(booking -> !"CANCELLED".equals(booking.getStatus()))
                .orElse(false);
    }
}