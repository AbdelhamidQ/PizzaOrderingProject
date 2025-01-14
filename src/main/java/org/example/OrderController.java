package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class OrderController {
    private Model model;
    private NewOrderView view;
    private DatabaseManager dbManager;

    public OrderController(Model model, NewOrderView view, DatabaseManager dbManager) {
        this.model = model;
        this.view = view;
        this.dbManager = dbManager;

        // Action listeners
        view.addPizza.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addPizza();
            }
        });

        view.submitOrder.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                submitOrder();
            }
        });

        view.restartOrder.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                restartOrder();
            }
        });
    }

    private void addPizza() {
        if (!validateSelections()) {
            JOptionPane.showMessageDialog(view, "Please select both size and crust type!");
            return;
        }

        Pizza pizza = getPizza();
        model.addPizza(pizza);

        view.orderDetails.append("Pizza #" + model.getPizzas().size() + ": " + pizza + "\n");
        clearSelections();
    }

    private Pizza getPizza() {
        String crust = view.panCrust.isSelected() ? "Pan" : view.stuffedCrust.isSelected() ? "Stuffed" : "Regular";
        String size = view.smallSize.isSelected() ? "Small" : view.mediumSize.isSelected() ? "Medium" : "Large";

        ArrayList<String> toppings = new ArrayList<>();
        if (view.extraCheese.isSelected()) toppings.add("Extra Cheese");
        if (view.greenPeppers.isSelected()) toppings.add("Green Peppers");
        if (view.tomatoes.isSelected()) toppings.add("Tomatoes");
        if (view.onions.isSelected()) toppings.add("Onions");
        if (view.olives.isSelected()) toppings.add("Olives");

        return new Pizza(size, crust, toppings);
    }

    private void submitOrder() {
        if (validateOrder()) {
            String customerName = view.cNameField.getText().trim();
            model.calculateTotalCost();

            int orderId = dbManager.saveOrder(customerName, model.getTotalCost(), model.getPizzas());

            if (orderId != -1) {
                JOptionPane.showMessageDialog(view,
                        "Order submitted successfully!\nOrder ID: " + orderId +
                                "\nTotal Cost: $" + String.format("%.2f", model.getTotalCost()));
                view.dispose();
            } else {
                JOptionPane.showMessageDialog(view,
                        "Error submitting order. Please try again.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private boolean validateOrder() {
        if (view.cNameField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(view,
                    "Please enter customer name!",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (model.getPizzas().isEmpty()) {
            JOptionPane.showMessageDialog(view,
                    "Please add at least one pizza to the order!",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }

        return true;
    }

    private boolean validateSelections() {
        boolean sizeSelected = view.smallSize.isSelected() ||
                view.mediumSize.isSelected() ||
                view.largeSize.isSelected();

        boolean crustSelected = view.panCrust.isSelected() ||
                view.stuffedCrust.isSelected() ||
                view.regularCrust.isSelected();

        return sizeSelected && crustSelected;
    }

    private void clearSelections() {
        
        ButtonGroup sizeGroup = new ButtonGroup();
        sizeGroup.add(view.smallSize);
        sizeGroup.add(view.mediumSize);
        sizeGroup.add(view.largeSize);
        sizeGroup.clearSelection();

        
        ButtonGroup crustGroup = new ButtonGroup();
        crustGroup.add(view.panCrust);
        crustGroup.add(view.stuffedCrust);
        crustGroup.add(view.regularCrust);
        crustGroup.clearSelection();

       
        view.extraCheese.setSelected(false);
        view.greenPeppers.setSelected(false);
        view.tomatoes.setSelected(false);
        view.onions.setSelected(false);
        view.olives.setSelected(false);
    }

    private void restartOrder() {
        model.resetOrder();
        view.orderDetails.setText("");
        view.cNameField.setText("");
        clearSelections();
    }
}