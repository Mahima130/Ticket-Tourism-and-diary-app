package com.Mahima.app.model;

import jakarta.persistence.*;

@Entity
@Table(name = "holiday_package") // optional, but good to match SQL table
public class HolidayPackage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String duration;
    private String hotelType;
    private String facilities;
    private double price;
    private String imageUrl;

    // Constructors
    public HolidayPackage() {
    }

    public HolidayPackage(Long id, String name, String duration, String hotelType, String facilities, double price, String imageUrl) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.hotelType = hotelType;
        this.facilities = facilities;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getHotelType() {
        return hotelType;
    }

    public void setHotelType(String hotelType) {
        this.hotelType = hotelType;
    }

    public String getFacilities() {
        return facilities;
    }

    public void setFacilities(String facilities) {
        this.facilities = facilities;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
