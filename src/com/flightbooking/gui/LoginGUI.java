package com.flightbooking.gui;

import javax.swing.*;
import java.awt.*;

/**
 * Graphical user interface for user login.
 *
 * @author Heba Salem
 * @version 1.0
 */
public class LoginGUI extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    /**
     * Creates and displays the login window.
     */
    public LoginGUI() {

        setTitle("Sky Booker");
        setSize(480, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 1. Create the panel with a clean GridBagLayout
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(25, 35, 25, 35));

        // 2. Set an elegant, clean background color (Soft off-white)
        panel.setBackground(new Color(241, 241, 241));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // --- ROW 0: LOGO & TITLE HEADER ---
        // Logo (Make sure the image is saved at: src/images/skyLogo.png)
        ImageIcon logo = new ImageIcon("src/images/skyLogo.png");
        Image scaledImage = logo.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        ImageIcon scaledLogo = new ImageIcon(scaledImage);
        JLabel logoLabel = new JLabel(scaledLogo);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(logoLabel, gbc);

        // Title
        JLabel title = new JLabel("Sky Booker");
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(new Color(24, 43, 73)); // Elegant deep airline navy blue

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.7;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(title, gbc);

        // --- ROW 1: USERNAME ---
        Font labelFont = new Font("Segoe UI", Font.BOLD, 13);
        Font inputFont = new Font("Segoe UI", Font.PLAIN, 14);
        Color textDark = new Color(50, 50, 50);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(labelFont);
        usernameLabel.setForeground(textDark);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        panel.add(usernameLabel, gbc);

        usernameField = new JTextField(15);
        usernameField.setFont(inputFont);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        panel.add(usernameField, gbc);

        // --- ROW 2: PASSWORD ---
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(labelFont);
        passwordLabel.setForeground(textDark);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        panel.add(passwordLabel, gbc);

        passwordField = new JPasswordField(15);
        passwordField.setFont(inputFont);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        panel.add(passwordField, gbc);

        // --- ROW 3: LOGIN BUTTON ---
        loginButton = new JButton("Login");
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 14));

        // Match button design with the deep logo navy
        loginButton.setBackground(new Color(24, 43, 73));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setPreferredSize(new Dimension(110, 36));

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(loginButton, gbc);

        add(panel);

        // Login Action Handling
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (username.equals("admin") && password.equals("admin123")) {
                JOptionPane.showMessageDialog(this, "Admin Login Successful");
                new FlightGUI();
                dispose();
            } else if (username.equals("passenger") && password.equals("pass123")) {
                JOptionPane.showMessageDialog(this, "Passenger Login Successful");
                new BookingGUI();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Username or Password", "Login Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        setVisible(true);
    }
}