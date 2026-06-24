package com.flightbooking.gui;

import com.flightbooking.database.DatabaseManager;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
/**
 * Graphical user interface used to
 * create and manage flight bookings.
 *
 * @author Heba Salem
 * @version 1.0
 */
public class BookingGUI extends JFrame {

    private JTextField passengerField;
    private JTextField flightIdField;
    private JButton bookButton;
    private JButton viewButton;
    private JButton cancelButton;
    /**
     * Creates and displays the booking window.
     */
    public BookingGUI() {

        setTitle("Book Flight");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2, 10, 10));

        passengerField = new JTextField();
        flightIdField = new JTextField();

        bookButton = new JButton("Book Flight");
        viewButton = new JButton("View Bookings");
        cancelButton = new JButton("Cancel Booking");

        panel.add(new JLabel("Passenger Name:"));
        panel.add(passengerField);

        panel.add(new JLabel("Flight ID:"));
        panel.add(flightIdField);

        panel.add(bookButton);
        panel.add(viewButton);
        panel.add(cancelButton);

        add(panel);

        bookButton.addActionListener(e -> {

            try {

                String passenger =
                        passengerField.getText();

                int flightId =
                        Integer.parseInt(
                                flightIdField.getText());

                Connection conn =
                        DatabaseManager.connect();

                String sql =
                        "INSERT INTO bookings "
                                + "(passenger_name, flight_id) "
                                + "VALUES (?, ?)";

                PreparedStatement pstmt =
                        conn.prepareStatement(sql);

                pstmt.setString(1, passenger);
                pstmt.setInt(2, flightId);

                pstmt.executeUpdate();

                JOptionPane.showMessageDialog(this,
                        "Booking Successful");

            } catch (SQLException ex) {

                JOptionPane.showMessageDialog(this,
                        "Database Error");

            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(this,
                        "Flight ID must be a number");
            }
        });
        viewButton.addActionListener(e -> {

            try {

                Connection conn = DatabaseManager.connect();

                String sql = "SELECT * FROM bookings";

                Statement stmt = conn.createStatement();

                ResultSet rs = stmt.executeQuery(sql);

                StringBuilder bookings = new StringBuilder();

                while (rs.next()) {

                    bookings.append("Booking ID: ")
                            .append(rs.getInt("booking_id"))
                            .append(" | Passenger: ")
                            .append(rs.getString("passenger_name"))
                            .append(" | Flight ID: ")
                            .append(rs.getInt("flight_id"))
                            .append("\n");
                }

                JOptionPane.showMessageDialog(this,
                        bookings.toString());

            } catch (SQLException ex) {

                JOptionPane.showMessageDialog(this,
                        "Error Loading Bookings");
            }
        });
        cancelButton.addActionListener(e -> {

            try {

                String bookingIdStr = JOptionPane.showInputDialog(
                        this,
                        "Enter Booking ID to cancel:");

                int bookingId = Integer.parseInt(bookingIdStr);

                Connection conn = DatabaseManager.connect();

                String sql =
                        "DELETE FROM bookings WHERE booking_id = ?";

                PreparedStatement pstmt =
                        conn.prepareStatement(sql);

                pstmt.setInt(1, bookingId);

                int rows = pstmt.executeUpdate();

                if (rows > 0) {

                    JOptionPane.showMessageDialog(this,
                            "Booking Cancelled Successfully");

                } else {

                    JOptionPane.showMessageDialog(this,
                            "Booking ID Not Found");
                }

            } catch (SQLException ex) {

                JOptionPane.showMessageDialog(this,
                        "Database Error");

            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(this,
                        "Invalid Booking ID");
            }
        });

        setVisible(true);
    }
}