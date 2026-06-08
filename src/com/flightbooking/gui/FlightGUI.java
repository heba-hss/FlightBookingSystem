package com.flightbooking.gui;

import javax.swing.*;
import java.awt.*;
import com.flightbooking.database.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FlightGUI extends JFrame {

    private JTextField idField;
    private JTextField destinationField;
    private JTextField timeField;
    private JTextField priceField;

    private JButton addButton;

    public FlightGUI() {

        setTitle("Add Flight");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2, 10, 10));

        JLabel idLabel = new JLabel("Flight ID:");
        JLabel destinationLabel = new JLabel("Destination:");
        JLabel timeLabel = new JLabel("Departure Time:");
        JLabel priceLabel = new JLabel("Price:");

        idField = new JTextField();
        destinationField = new JTextField();
        timeField = new JTextField();
        priceField = new JTextField();

        addButton = new JButton("Add Flight");

        panel.add(idLabel);
        panel.add(idField);

        panel.add(destinationLabel);
        panel.add(destinationField);

        panel.add(timeLabel);
        panel.add(timeField);

        panel.add(priceLabel);
        panel.add(priceField);

        panel.add(new JLabel());
        panel.add(addButton);

        add(panel);

        setVisible(true);
        addButton.addActionListener(e -> {

            try {

                int id = Integer.parseInt(idField.getText());
                String destination = destinationField.getText();
                String time = timeField.getText();
                double price = Double.parseDouble(priceField.getText());

                Connection conn = DatabaseManager.connect();

                String sql = "INSERT INTO flights "
                        + "(flight_id, destination, departure_time, price) "
                        + "VALUES (?, ?, ?, ?)";

                PreparedStatement pstmt = conn.prepareStatement(sql);

                pstmt.setInt(1, id);
                pstmt.setString(2, destination);
                pstmt.setString(3, time);
                pstmt.setDouble(4, price);

                pstmt.executeUpdate();

                JOptionPane.showMessageDialog(this,
                        "Flight Added Successfully");

            } catch (SQLException ex) {

                JOptionPane.showMessageDialog(this,
                        "Database Error");

            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(this,
                        "Please enter valid numbers");
            }
        });
    }
}