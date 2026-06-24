package com.flightbooking.model;

/**
 * Represents a flight in the booking system.
 */
public class Flight {

    private int flightId;
    private String destination;
    private String departureTime;
    private double price;

    /**
     * Creates a flight object.
     *
     * @param flightId Flight ID
     * @param destination Flight destination
     * @param departureTime Flight departure time
     * @param price Flight price
     */
    public Flight(int flightId, String destination,
                  String departureTime, double price) {

        this.flightId = flightId;
        this.destination = destination;
        this.departureTime = departureTime;
        this.price = price;
    }

    // Getters
    public int getFlightId() {
        return flightId;
    }

    public String getDestination() {
        return destination;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public double getPrice() {
        return price;
    }

    // Setters
    public void setFlightId(int flightId) {
        this.flightId = flightId;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Displays flight information.
     */
    public void displayFlightInfo() {

        System.out.println("Flight ID: " + flightId);
        System.out.println("Destination: " + destination);
        System.out.println("Departure Time: " + departureTime);
        System.out.println("Price: " + price);
    }
}