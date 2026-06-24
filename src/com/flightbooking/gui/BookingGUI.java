package com.flightbooking.gui;

import com.flightbooking.database.DatabaseManager;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

        setTitle("Book Flight - Sky Booker");
        setSize(450, 280); // Adjusted size to properly balance components
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main Panel Config
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(245, 247, 250)); // Matching soft off-white background
        panel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Fonts & Styling
        Font labelFont = new Font("Segoe UI", Font.BOLD, 13);
        Font inputFont = new Font("Segoe UI", Font.PLAIN, 14);
        Color navyColor = new Color(24, 43, 73);
        Color textDark = new Color(50, 50, 50);

        // --- ROW 0: PASSENGER FIELD ---
        JLabel passengerLabel = new JLabel("Passenger Name:");
        passengerLabel.setFont(labelFont);
        passengerLabel.setForeground(textDark);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        panel.add(passengerLabel, gbc);

        passengerField = new JTextField(15);
        passengerField.setFont(inputFont);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.7;
        panel.add(passengerField, gbc);

        // --- ROW 1: FLIGHT ID FIELD ---
        JLabel flightIdLabel = new JLabel("Flight ID:");
        flightIdLabel.setFont(labelFont);
        flightIdLabel.setForeground(textDark);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        panel.add(flightIdLabel, gbc);

        flightIdField = new JTextField(15);
        flightIdField.setFont(inputFont);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        panel.add(flightIdField, gbc);

        // --- ROW 2: PRIMARY BUTTON (BOOK FLIGHT) ---
        bookButton = new JButton("Book Flight");
        bookButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        bookButton.setBackground(navyColor);
        bookButton.setForeground(Color.WHITE);
        bookButton.setFocusPainted(false);
        bookButton.setPreferredSize(new Dimension(120, 36));

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2; // Spans across both layout columns smoothly
        gbc.insets = new Insets(15, 8, 5, 8); // Added a larger top margin for visual separation
        panel.add(bookButton, gbc);

        // --- ROW 3: UTILITY ACTION BUTTONS ---
        // Reset gridwidth for sub-actions
        gbc.gridwidth = 1;
        gbc.insets = new Insets(5, 8, 5, 8);

        viewButton = new JButton("View Bookings");
        viewButton.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        viewButton.setFocusPainted(false);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.5;
        panel.add(viewButton, gbc);

        cancelButton = new JButton("Cancel Booking");
        cancelButton.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        cancelButton.setFocusPainted(false);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.weightx = 0.5;
        panel.add(cancelButton, gbc);

        add(panel);

        // --- DATABASE BUSINESS LOGIC (WITH AUTO-CLOSE CONTROLS) ---

        // Action: Book Flight
        bookButton.addActionListener(e -> {
            String passenger = passengerField.getText().trim();
            String flightIdStr = flightIdField.getText().trim();

            if (passenger.isEmpty() || flightIdStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Validation Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Using Try-With-Resources to automatically manage data pipeline safely
            String sql = "INSERT INTO bookings (passenger_name, flight_id) VALUES (?, ?)";
            try (Connection conn = DatabaseManager.connect();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                int flightId = Integer.parseInt(flightIdStr);
                pstmt.setString(1, passenger);
                pstmt.setInt(2, flightId);
                pstmt.executeUpdate();

                JOptionPane.showMessageDialog(this, "Booking Successful!");
                passengerField.setText("");
                flightIdField.setText("");

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Flight ID must be a valid number.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Action: View Bookings
        viewButton.addActionListener(e -> {
            String sql = "SELECT * FROM bookings";

            try (Connection conn = DatabaseManager.connect();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {

                StringBuilder bookings = new StringBuilder();
                boolean dataFound = false;

                while (rs.next()) {
                    dataFound = true;
                    bookings.append("🎟️ Booking ID: ").append(rs.getInt("booking_id"))
                            .append("  |  ✈️ Flight ID: ").append(rs.getInt("flight_id"))
                            .append("\n👤 Passenger: ").append(rs.getString("passenger_name"))
                            .append("\n--------------------------------------------------\n");
                }

                if (!dataFound) {
                    JOptionPane.showMessageDialog(this, "No active bookings found.", "Sky Booker", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    // Displaying in a simple scrollable layout in case the lists get long
                    JTextArea textArea = new JTextArea(bookings.toString());
                    textArea.setEditable(false);
                    JScrollPane scrollPane = new JScrollPane(textArea);
                    scrollPane.setPreferredSize(new Dimension(400, 250));
                    JOptionPane.showMessageDialog(this, scrollPane, "Current Bookings", JOptionPane.PLAIN_MESSAGE);
                }

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error Loading Bookings.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Action: Cancel Booking
        cancelButton.addActionListener(e -> {
            String bookingIdStr = JOptionPane.showInputDialog(this, "Enter Booking ID to cancel:");
            if (bookingIdStr == null || bookingIdStr.trim().isEmpty()) return;

            String sql = "DELETE FROM bookings WHERE booking_id = ?";
            try (Connection conn = DatabaseManager.connect();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                int bookingId = Integer.parseInt(bookingIdStr.trim());
                pstmt.setInt(1, bookingId);
                int rows = pstmt.executeUpdate();

                if (rows > 0) {
                    JOptionPane.showMessageDialog(this, "Booking Cancelled Successfully.");
                } else {
                    JOptionPane.showMessageDialog(this, "Booking ID not found.", "Not Found", JOptionPane.WARNING_MESSAGE);
                }

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Database Error.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid Booking ID format.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        setVisible(true);
    }
}