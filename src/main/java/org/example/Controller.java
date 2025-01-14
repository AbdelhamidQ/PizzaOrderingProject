package org.example;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Controller {
    private Model model;
    NewOrderView view;

    public Controller(Model model, NewOrderView view) {
        this.model = model;
        this.view = view;

        // Action listeners
        view.addPizza.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addPizza();
            }
        });

        view.submitOrder.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                calculateTotal();
            }
        });

        view.restartOrder.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                restartOrder();
            }
        });
    }

    private void addPizza() {
        String size = view.smallSize.isSelected() ? "Small" : view.mediumSize.isSelected() ? "Medium" : "Large";
        String crust = view.panCrust.isSelected() ? "Pan" : view.stuffedCrust.isSelected() ? "Stuffed" : "Regular";

        ArrayList<String> toppings = new ArrayList<>();
        if (view.extraCheese.isSelected()) toppings.add("Extra Cheese");
        if (view.greenPeppers.isSelected()) toppings.add("Green Peppers");
        if (view.tomatoes.isSelected()) toppings.add("Tomatoes");
        if (view.onions.isSelected()) toppings.add("Onions");
        if (view.olives.isSelected()) toppings.add("Olives");

        Pizza pizza = new Pizza(size, crust, toppings);
        model.addPizza(pizza);

        view.orderDetails.append("Pizza #" + model.getPizzas().size() + ": " + pizza + "\n");
    }

    private void calculateTotal() {
        model.calculateTotalCost();
//        view.totalField.setText(String.valueOf(model.getTotalCost()));
        view.orderDetails.append("Total Cost is: " + model.getTotalCost() + "\n");
    }

    private void restartOrder() {
        model.resetOrder();
//        view.totalField.setText("");
        view.orderDetails.setText("");
    }
}



