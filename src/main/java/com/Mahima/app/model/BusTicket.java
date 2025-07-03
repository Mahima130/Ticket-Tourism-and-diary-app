package com.Mahima.app.model;

import jakarta.persistence.*; // Use jakarta.persistence for Spring Boot 3+
import java.time.LocalDate;

@Entity
@Table(name = "bus_tickets") // Recommended
public class BusTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ⭐ CHANGE START ⭐
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    // ⭐ CHANGE END ⭐

    private String busNumber;
    private String departureLocation;
    private String arrivalLocation;
    private LocalDate travelDate;
    private int numberOfSeats;
    // REMOVE: private String passengerName; // Remove this if you had it

    private double price;
    private String status; // e.g., CONFIRMED, CANCELLED

    // Constructors, Getters, Setters (similar to FlightTicket, make sure to add for 'user')
    public BusTicket() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    // ⭐ NEW GETTER/SETTER FOR USER ⭐
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getBusNumber() { return busNumber; }
    public void setBusNumber(String busNumber) { this.busNumber = busNumber; }

    public String getDepartureLocation() { return departureLocation; }
    public void setDepartureLocation(String departureLocation) { this.departureLocation = departureLocation; }

    public String getArrivalLocation() { return arrivalLocation; }
    public void setArrivalLocation(String arrivalLocation) { this.arrivalLocation = arrivalLocation; }

    public LocalDate getTravelDate() { return travelDate; }
    public void setTravelDate(LocalDate travelDate) { this.travelDate = travelDate; }

    public int getNumberOfSeats() { return numberOfSeats; }
    public void setNumberOfSeats(int numberOfSeats) { this.numberOfSeats = numberOfSeats; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}