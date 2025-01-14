package org.example;

import javax.swing.*;
import java.awt.*;

public class NewOrderView extends JFrame {
    public JTextField cNameField;
    public JRadioButton smallSize, mediumSize, largeSize;
    public JRadioButton panCrust, stuffedCrust, regularCrust;
    public JCheckBox extraCheese, tomatoes, olives, greenPeppers, onions;
    public JButton addPizza, submitOrder, restartOrder;
    public JTextArea orderDetails;

    public NewOrderView() {

        setTitle("Pizza Order App");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout());
        JLabel cName = new JLabel("Customer Name:");
        cNameField = new JTextField(10);
        topPanel.add(cName);
        topPanel.add(cNameField);
        add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(2, 2, 10, 10));

        // Size
        JPanel sizePanel = new JPanel(new GridLayout(3, 1));
        sizePanel.setBorder(BorderFactory.createTitledBorder("Size"));
        smallSize = new JRadioButton("Small");
        mediumSize = new JRadioButton("Medium");
        largeSize = new JRadioButton("Large");

        sizePanel.add(smallSize);
        sizePanel.add(mediumSize);
        sizePanel.add(largeSize);

        ButtonGroup sizeGroup = new ButtonGroup();
        sizeGroup.add(smallSize);
        sizeGroup.add(mediumSize);
        sizeGroup.add(largeSize);

        // Crust
        JPanel crustPanel = new JPanel(new GridLayout(3, 1));
        crustPanel.setBorder(BorderFactory.createTitledBorder("Crust"));
        panCrust = new JRadioButton("Pan");
        stuffedCrust = new JRadioButton("Stuffed");
        regularCrust = new JRadioButton("Regular");

        crustPanel.add(panCrust);
        crustPanel.add(stuffedCrust);
        crustPanel.add(regularCrust);

        ButtonGroup crustGroup = new ButtonGroup();
        crustGroup.add(panCrust);
        crustGroup.add(stuffedCrust);
        crustGroup.add(regularCrust);

        // Toppings
        JPanel toppingsPanel = new JPanel(new GridLayout(3, 2));
        toppingsPanel.setBorder(BorderFactory.createTitledBorder("Toppings"));
        extraCheese = new JCheckBox("Extra Cheese");
        greenPeppers = new JCheckBox("Green Peppers");
        tomatoes = new JCheckBox("Tomatoes");
        onions = new JCheckBox("Onions");
        olives = new JCheckBox("Olives");



        toppingsPanel.add(extraCheese);
        toppingsPanel.add(greenPeppers);
        toppingsPanel.add(tomatoes);
        toppingsPanel.add(onions);
        toppingsPanel.add(olives);

        // Buttons
        JPanel buttonsPanel = new JPanel(new GridLayout(3,1,0,20));
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        addPizza = new JButton("Add Pizza");
        submitOrder = new JButton("Submit Order");
        restartOrder = new JButton("Restart Order");
        buttonsPanel.add(addPizza);
        buttonsPanel.add(submitOrder);
        buttonsPanel.add(restartOrder);

        centerPanel.add(sizePanel);
        centerPanel.add(toppingsPanel);
        centerPanel.add(crustPanel);
        centerPanel.add(buttonsPanel);

        add(centerPanel, BorderLayout.CENTER);

        orderDetails = new JTextArea(10, 40);
        orderDetails.setEditable(false);
        JPanel detailsPanel =new JPanel(new FlowLayout());
        JScrollPane scrollPane = new JScrollPane(orderDetails);
        detailsPanel.add(scrollPane);
        add(detailsPanel, BorderLayout.SOUTH);
    }
}