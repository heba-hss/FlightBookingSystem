package com.flightbooking.model;

/**
 * Represents a flight booking.
 */
public class Booking {

    private int bookingId;
    private Passenger passenger;
    private Flight flight;

    // Constructor
    public Booking(int bookingId, Passenger passenger, Flight flight) {

        this.bookingId = bookingId;
        this.passenger = passenger;
        this.flight = flight;
    }

    // Getters
    public int getBookingId() {
        return bookingId;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public Flight getFlight() {
        return flight;
    }

    // Setters
    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    /**
     * Displays booking information.
     */
    public void displayBookingInfo() {

        System.out.println("Booking ID: " + bookingId);
        System.out.println("Passenger: " + passenger.getName());
        System.out.println("Flight Destination: " + flight.getDestination());
    }
}