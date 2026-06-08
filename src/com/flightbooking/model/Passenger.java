package com.flightbooking.model;

import com.flightbooking.interfaces.Loginable;

public class Passenger extends Person implements Loginable {

    public Passenger(String name, String username, String password) {
        super(name, username, password);
    }

    @Override
    public void displayInfo() {
        System.out.println("Passenger Information");
    }

    @Override
    public boolean login(String username, String password) {
        return false;
    }
}
