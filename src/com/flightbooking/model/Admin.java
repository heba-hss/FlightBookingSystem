package com.flightbooking.model;

import com.flightbooking.interfaces.Loginable;

public class Admin extends Person implements Loginable {

    public Admin(String name, String username, String password) {
        super(name, username, password);
    }

    @Override
    public void displayInfo() {
        System.out.println("Admin Information");
    }

    @Override
    public boolean login(String username, String password) {
        return false;
    }
}
