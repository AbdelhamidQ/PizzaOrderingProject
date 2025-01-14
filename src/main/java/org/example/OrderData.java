package org.example;

import java.sql.Timestamp;

public class OrderData {
    private int orderId;
    private String customerName;
    private Timestamp orderDate;
    private double totalCost;
    private String details;

    public OrderData(int orderId, String customerName, Timestamp orderDate, double totalCost, String details) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.orderDate = orderDate;
        this.totalCost = totalCost;
        this.details = details;
    }

    public int getOrderId() {
        return orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public Timestamp getOrderDate() {
        return orderDate;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public String getDetails() {
        return details;
    }
}