package com.Mahima.app.model;

import jakarta.persistence.*;

@Entity
public class TrainRoute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String trainNumber;
    private String trainName;

    // Corrected field names
    private String source;
    private String destination;

    private int acSeats;
    private int sleeperSeats;
    private int generalSeats;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTrainNumber() { return trainNumber; }
    public void setTrainNumber(String trainNumber) { this.trainNumber = trainNumber; }

    public String getTrainName() { return trainName; }
    public void setTrainName(String trainName) { this.trainName = trainName; }

    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }

    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }

    public int getAcSeats() { return acSeats; }
    public void setAcSeats(int acSeats) { this.acSeats = acSeats; }

    public int getSleeperSeats() { return sleeperSeats; }
    public void setSleeperSeats(int sleeperSeats) { this.sleeperSeats = sleeperSeats; }

    public int getGeneralSeats() { return generalSeats; }
    public void setGeneralSeats(int generalSeats) { this.generalSeats = generalSeats; }
}
