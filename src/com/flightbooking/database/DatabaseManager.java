package com.flightbooking.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {

    private static final String URL =
            "jdbc:sqlite:flightbooking.db";

    public static Connection connect() {

        Connection conn = null;

        try {

            conn = DriverManager.getConnection(URL);
            System.out.println("Database Connected Successfully");

        } catch (SQLException e) {

            System.out.println("Connection Error: "
                    + e.getMessage());
        }

        return conn;
    }
    public static void createFlightsTable() {

        String sql = "CREATE TABLE IF NOT EXISTS flights ("
                + "flight_id INTEGER PRIMARY KEY, "
                + "destination TEXT NOT NULL, "
                + "departure_time TEXT NOT NULL, "
                + "price REAL NOT NULL"
                + ");";

        try (Connection conn = connect();
             var stmt = conn.createStatement()) {

            stmt.execute(sql);
            System.out.println("Flights Table Created");

        } catch (SQLException e) {

            System.out.println("Table Error: "
                    + e.getMessage());
        }
    }
}