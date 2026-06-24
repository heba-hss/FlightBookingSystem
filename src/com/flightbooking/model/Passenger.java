package com.flightbooking.model;

import com.flightbooking.interfaces.Loginable;
/**
 * Represents a passenger who can search, book,
 * and cancel flight reservations.
 *
 * @author Heba Salem
 * @version 1.0
 */
public class Passenger extends Person implements Loginable {

    public Passenger(String name, String username, String password) {
        super(name, username, password);
    }
    /**
     * Displays passenger information.
     */
    @Override
    public void displayInfo() {
        System.out.println("Passenger Information");
    }
    /**
     * Authenticates the passenger.
     *
     * @param username entered username
     * @param password entered password
     * @return true if login is successful, otherwise false
     */
    @Override
    public boolean login(String username, String password) {
        return false;
    }
}
