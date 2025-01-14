package org.example;

import java.util.ArrayList;

public class Pizza {
    private String size;
    private String crust;
    private ArrayList<String> toppings;

    public Pizza(String size, String crust, ArrayList<String> toppings) {
        this.size = size;
        this.crust = crust;
        this.toppings = toppings;
    }

    public double calcCost() {
        double price = 0;

        if (size.equals("Small")) price = 5;
        else if (size.equals("Medium")) price = 10;
        else if (size.equals("Large")) price = 15.0;

        if (crust.equals("Stuffed")) price += 3.0;
        else if (crust.equals("Pan")) price += 2.0;
        else if (crust.equals("Regular")) price += 1.5;

        price += toppings.size() * 0.5;
        return price;
    }

    @Override
    public String toString() {
        return "Size: " + size + ", Crust: " + crust + ", Toppings: " + String.join(", ", toppings);
    }

    public String getCrust() {
        return crust;
    }

    public String getSize() {
        return size;
    }

    public ArrayList<String> getToppings() {
        return toppings;
    }
}
