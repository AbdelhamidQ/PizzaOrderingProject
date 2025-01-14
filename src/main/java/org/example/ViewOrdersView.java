package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ViewOrdersView extends JFrame {
    private JTextField searchField;
    private JButton searchButton;
    private JButton backButton;
    private JTable ordersTable;
    private DefaultTableModel tableModel;

    public ViewOrdersView() {
        setTitle("View Orders");
        setSize(800, 600);
        setLayout(new BorderLayout(10, 10));

        // Search panel
        JPanel searchPanel = new JPanel(new FlowLayout());
        searchField = new JTextField(20);
        searchButton = new JButton("Search");
        searchPanel.add(new JLabel("Customer Name:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        // Table
        String[] columns = {"Order ID", "Customer Name", "Order Date", "Total", "Order Details"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;  // Make table read-only
            }
        };

        ordersTable = new JTable(tableModel);
        ordersTable.getColumnModel().getColumn(4).setPreferredWidth(300); // Make details column wider
        JScrollPane scrollPane = new JScrollPane(ordersTable);

        // Back button
        backButton = new JButton("Back to Menu");

        // Add components
        add(searchPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(backButton, BorderLayout.SOUTH);

        // Center the frame
        setLocationRelativeTo(null);
    }

    public JTextField getSearchField() {
        return searchField;
    }

    public JButton getSearchButton() {
        return searchButton;
    }

    public JButton getBackButton() {
        return backButton;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }
}