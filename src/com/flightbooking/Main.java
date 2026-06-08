package com.flightbooking;

import com.flightbooking.database.DatabaseManager;
import com.flightbooking.gui.FlightGUI;

public class Main {

    public static void main(String[] args) {

        DatabaseManager.createFlightsTable();

        new FlightGUI();
    }
}