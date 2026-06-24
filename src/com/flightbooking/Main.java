package com.flightbooking;

import com.flightbooking.database.DatabaseManager;
import com.flightbooking.gui.BookingGUI;
import com.flightbooking.gui.FlightGUI;
import com.flightbooking.gui.LoginGUI;

/**
 * Entry point of the Flight Booking System.
 *
 * @author Heba Salem
 * @version 1.0
 */
public class Main {
    /**
     * Starts the application and initializes
     * database tables.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {

        DatabaseManager.createFlightsTable();
        DatabaseManager.createBookingsTable();

        new LoginGUI();
    }
}