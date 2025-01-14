package org.example;

import java.util.ArrayList;

public class Model {
    private ArrayList<Pizza> pizzas;
    private String customerName;
    private double totalCost;

    public Model() {
        pizzas = new ArrayList<>();
    }

    public void addPizza(Pizza pizza) {
        pizzas.add(pizza);
    }

    public void calculateTotalCost() {
        totalCost = 0;
        for (Pizza pizza : pizzas) {
            totalCost += pizza.calcCost();
        }
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void resetOrder() {
        pizzas.clear();
        totalCost = 0;
    }

    public ArrayList<Pizza> getPizzas() {
        return pizzas;
    }
}
