package com.flightbooking.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 * Manages database connections and
 * table creation operations.
 *
 * @author Heba Salem
 * @version 1.0
 */
public class DatabaseManager {

    private static final String URL =
            "jdbc:sqlite:flightbooking.db";
    /**
     * Creates a connection to the SQLite database.
     *
     * @return database connection object
     */
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
    /**
     * Creates the flights table if it does not exist.
     */
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
    /**
     * Creates the bookings table if it does not exist.
     */
    public static void createBookingsTable() {

        String sql = "CREATE TABLE IF NOT EXISTS bookings ("
                + "booking_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "passenger_name TEXT NOT NULL, "
                + "flight_id INTEGER NOT NULL"
                + ");";

        try (Connection conn = connect();
             var stmt = conn.createStatement()) {

            stmt.execute(sql);
            System.out.println("Bookings Table Created");

        } catch (SQLException e) {

            System.out.println("Table Error: "
                    + e.getMessage());
        }
    }
}