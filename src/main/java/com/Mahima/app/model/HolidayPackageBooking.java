package com.Mahima.app.model;

import jakarta.persistence.*; // Make sure you are using jakarta.persistence
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "holiday_package_booking") // Ensure your table name matches
public class HolidayPackageBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "booking_date")
    private LocalDateTime bookingDate; // Using LocalDateTime for date and time

    @Column(name = "package_name")
    private String packageName;

    private double price;

    @ManyToOne // Many bookings can belong to one user
    @JoinColumn(name = "user_id", nullable = false) // Link to the User entity's primary key
    private User user; // This maps to the user_id column

    // ⭐ NEW: Add the status field ⭐
    @Column(name = "status") // This will map to a 'status' column in your DB
    private String status; // Or use an Enum if you have a fixed set of statuses (e.g., CONFIRMED, CANCELLED)

    // Default constructor (required by JPA)
    public HolidayPackageBooking() {
        // Set default status for new bookings
        this.status = "CONFIRMED"; // Default status when a new booking is created
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDateTime bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // ⭐ NEW: Getter and Setter for status ⭐
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}