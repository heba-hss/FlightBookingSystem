package com.flightbooking.interfaces;
/**
 * Defines the authentication behavior
 * for system users.
 *
 * @author Heba Salem
 * @version 1.0
 */
public interface Loginable {
    /**
     * Validates user login credentials.
     *
     * @param username entered username
     * @param password entered password
     * @return true if credentials are valid
     */
    boolean login(String username, String password);
}
