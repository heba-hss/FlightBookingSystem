package com.flightbooking.model;

import com.flightbooking.interfaces.Loginable;
/**
 * Represents an administrator who manages
 * flights and system information.
 *
 * @author Heba Salem
 * @version 1.0
 */
public class Admin extends Person implements Loginable {

    public Admin(String name, String username, String password) {
        super(name, username, password);
    }
    /**
     * Displays administrator information.
     */
    @Override
    public void displayInfo() {
        System.out.println("Admin Information");
    }
    /**
     * Authenticates the administrator.
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
