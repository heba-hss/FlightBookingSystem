package com.flightbooking.gui;

import javax.swing.*;
import java.awt.*;
import com.flightbooking.database.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Graphical user interface used for
 * managing flight information.
 *
 * @author Heba Salem
 * @version 1.0
 */
public class FlightGUI extends JFrame {

    private JTextField idField;
    private JTextField destinationField;
    private JTextField timeField;
    private JTextField priceField;

    private JButton addButton;
    private JButton viewButton;
    private JTextField searchField;
    private JButton searchButton;

    /**
     * Creates and displays the flight management window.
     */
    public FlightGUI() {

        setTitle("Flight Management - Sky Booker");
        setSize(480, 480); // Adjusted size for proper balance and text rendering
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main structural Panel Setup
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(245, 247, 250)); // Soft off-white theme
        panel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Fonts & Palettes
        Font labelFont = new Font("Segoe UI", Font.BOLD, 13);
        Font inputFont = new Font("Segoe UI", Font.PLAIN, 14);
        Color navyColor = new Color(24, 43, 73);
        Color textDark = new Color(50, 50, 50);

        // --- ROW 0: FLIGHT ID ---
        JLabel idLabel = new JLabel("Flight ID:");
        idLabel.setFont(labelFont);
        idLabel.setForeground(textDark);
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.3;
        panel.add(idLabel, gbc);

        idField = new JTextField(15);
        idField.setFont(inputFont);
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 0.7;
        panel.add(idField, gbc);

        // --- ROW 1: DESTINATION ---
        JLabel destinationLabel = new JLabel("Destination:");
        destinationLabel.setFont(labelFont);
        destinationLabel.setForeground(textDark);
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        panel.add(destinationLabel, gbc);

        destinationField = new JTextField(15);
        destinationField.setFont(inputFont);
        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 1.0;
        panel.add(destinationField, gbc);

        // --- ROW 2: DEPARTURE TIME ---
        JLabel timeLabel = new JLabel("Departure Time:");
        timeLabel.setFont(labelFont);
        timeLabel.setForeground(textDark);
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0;
        panel.add(timeLabel, gbc);

        timeField = new JTextField(15);
        timeField.setFont(inputFont);
        gbc.gridx = 1; gbc.gridy = 2; gbc.weightx = 1.0;
        panel.add(timeField, gbc);

        // --- ROW 3: PRICE ---
        JLabel priceLabel = new JLabel("Price:");
        priceLabel.setFont(labelFont);
        priceLabel.setForeground(textDark);
        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0;
        panel.add(priceLabel, gbc);

        priceField = new JTextField(15);
        priceField.setFont(inputFont);
        gbc.gridx = 1; gbc.gridy = 3; gbc.weightx = 1.0;
        panel.add(priceField, gbc);

        // --- ROW 4: ACTION TRIGGER BUTTON (ADD FLIGHT) ---
        addButton = new JButton("Add Flight");
        addButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        addButton.setBackground(navyColor);
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);
        addButton.setPreferredSize(new Dimension(120, 36));

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        gbc.insets = new Insets(15, 6, 15, 6); // Add spacing top and bottom
        panel.add(addButton, gbc);

        // --- ROW 5: DIVIDER OR SEARCH SECTION TITLE ---
        JSeparator sep = new JSeparator();
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        gbc.insets = new Insets(5, 6, 15, 6);
        panel.add(sep, gbc);

        // --- ROW 6: SEARCH FIELD INPUT ---
        JLabel searchLabel = new JLabel("Search Destination:");
        searchLabel.setFont(labelFont);
        searchLabel.setForeground(textDark);
        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 1; gbc.weightx = 0;
        gbc.insets = new Insets(6, 6, 6, 6);
        panel.add(searchLabel, gbc);

        searchField = new JTextField(15);
        searchField.setFont(inputFont);
        gbc.gridx = 1; gbc.gridy = 6; gbc.weightx = 1.0;
        panel.add(searchField, gbc);

        // --- ROW 7: MANAGEMENT SYSTEM UTILITY ACTION UTILITIES ---
        JPanel utilityButtonPanel = new JPanel(new GridLayout(1, 2, 12, 0));
        utilityButtonPanel.setBackground(new Color(245, 247, 250));

        searchButton = new JButton("Search Flight");
        searchButton.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        searchButton.setFocusPainted(false);

        viewButton = new JButton("View Flights");
        viewButton.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        viewButton.setFocusPainted(false);

        utilityButtonPanel.add(searchButton);
        utilityButtonPanel.add(viewButton);

        gbc.gridx = 0; gbc.gridy = 7; gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 6, 6, 6);
        panel.add(utilityButtonPanel, gbc);

        add(panel);

        // --- EVENT HANDLING LOGIC WITH PREPARED DISCONNECT CONTROLS ---

        // Action: View Flights
        viewButton.addActionListener(e -> {
            String sql = "SELECT * FROM flights";
            try (Connection conn = DatabaseManager.connect();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {

                StringBuilder flights = new StringBuilder();
                boolean dataFound = false;

                while (rs.next()) {
                    dataFound = true;
                    flights.append("🆔 ID: ").append(rs.getInt("flight_id"))
                            .append("  |  📍 Dest: ").append(rs.getString("destination"))
                            .append("\n🕒 Time: ").append(rs.getString("departure_time"))
                            .append("  |  💵 Price: $").append(rs.getDouble("price"))
                            .append("\n--------------------------------------------------\n");
                }

                if (!dataFound) {
                    JOptionPane.showMessageDialog(this, "No flights listed in system database.", "Sky Booker", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JTextArea textArea = new JTextArea(flights.toString());
                    textArea.setEditable(false);
                    JScrollPane scrollPane = new JScrollPane(textArea);
                    scrollPane.setPreferredSize(new Dimension(420, 250));
                    JOptionPane.showMessageDialog(this, scrollPane, "Available Flights", JOptionPane.PLAIN_MESSAGE);
                }

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error Loading Flights.", "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Action: Search Flight
        searchButton.addActionListener(e -> {
            String destination = searchField.getText().trim();
            if (destination.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a destination to search.", "Validation Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String sql = "SELECT * FROM flights WHERE destination = ?";
            try (Connection conn = DatabaseManager.connect();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setString(1, destination);
                try (ResultSet rs = pstmt.executeQuery()) {
                    StringBuilder result = new StringBuilder();
                    while (rs.next()) {
                        result.append("Flight ID: ").append(rs.getInt("flight_id"))
                                .append("\nDestination: ").append(rs.getString("destination"))
                                .append("\nTime: ").append(rs.getString("departure_time"))
                                .append("\nPrice: $").append(rs.getDouble("price"))
                                .append("\n\n");
                    }

                    if (result.length() == 0) {
                        JOptionPane.showMessageDialog(this, "No flights found matching that destination.", "Search Results", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JTextArea textArea = new JTextArea(result.toString());
                        textArea.setEditable(false);
                        JScrollPane scrollPane = new JScrollPane(textArea);
                        scrollPane.setPreferredSize(new Dimension(380, 200));
                        JOptionPane.showMessageDialog(this, scrollPane, "Search Results", JOptionPane.PLAIN_MESSAGE);
                    }
                }

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Database error during execution.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Action: Add Flight
        addButton.addActionListener(e -> {
            String idStr = idField.getText().trim();
            String destination = destinationField.getText().trim();
            String time = timeField.getText().trim();
            String priceStr = priceField.getText().trim();

            if (idStr.isEmpty() || destination.isEmpty() || time.isEmpty() || priceStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All input data criteria must be filled out.", "Input Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String sql = "INSERT INTO flights (flight_id, destination, departure_time, price) VALUES (?, ?, ?, ?)";
            try (Connection conn = DatabaseManager.connect();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                int id = Integer.parseInt(idStr);
                double price = Double.parseDouble(priceStr);

                pstmt.setInt(1, id);
                pstmt.setString(2, destination);
                pstmt.setString(3, time);
                pstmt.setDouble(4, price);
                pstmt.executeUpdate();

                JOptionPane.showMessageDialog(this, "Flight Added Successfully.");

                // Clear out field values on completion
                idField.setText("");
                destinationField.setText("");
                timeField.setText("");
                priceField.setText("");

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Database processing fault: Flight ID might already exist.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid numerical format definitions for ID and Price fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        setVisible(true);
    }
}