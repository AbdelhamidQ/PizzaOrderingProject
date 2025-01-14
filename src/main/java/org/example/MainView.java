package org.example;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {
    private JButton newOrderButton, viewOrdersButton;

    public MainView() {
        setTitle("Pizza Order System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create buttons with larger size
        newOrderButton = new JButton("New Order");
        viewOrdersButton = new JButton("View Orders");

        // Make buttons bigger and the same size
        Dimension buttonSize = new Dimension(300, 80);
        newOrderButton.setPreferredSize(buttonSize);
        viewOrdersButton.setPreferredSize(buttonSize);

        // Use larger font for buttons
        Font buttonFont = new Font("Arial", Font.BOLD, 20);
        newOrderButton.setFont(buttonFont);
        viewOrdersButton.setFont(buttonFont);

        // Create main panel with padding
        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        mainPanel.setLayout(new GridLayout(2, 1, 0, 20)); // 2 rows, 1 column, 20px vertical gap

        // Add buttons to panel
        mainPanel.add(newOrderButton);
        mainPanel.add(viewOrdersButton);

        // Add panel to frame
        add(mainPanel);

        // Pack the frame to wrap around components
        pack();

        // Center the frame on screen
        setLocationRelativeTo(null);

        // Prevent frame resizing
        setResizable(false);
    }

    public JButton getNewOrderButton() {
        return newOrderButton;
    }

    public JButton getViewOrdersButton() {
        return viewOrdersButton;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainView mainView = new MainView();
            Model model = new Model();
            new MainController(model, mainView);
            mainView.setVisible(true);
        });
    }
}