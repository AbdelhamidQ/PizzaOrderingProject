package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class MainController {
    private Model model;
    private MainView mainView;
    private NewOrderView orderView;
    private ViewOrdersView viewOrdersView;
    private DatabaseManager dbManager;

    public MainController(Model model, MainView mainView) {
        this.model = model;
        this.mainView = mainView;
        this.dbManager = new DatabaseManager();

        // Add button listeners
        mainView.getNewOrderButton().addActionListener(e -> showNewOrder());
        mainView.getViewOrdersButton().addActionListener(e -> showViewOrders());
    }

    private void showNewOrder() {
        if (orderView == null) {
            orderView = new NewOrderView();
            Model orderModel = new Model();
            new OrderController(orderModel, orderView, dbManager);
        }
        orderView.setVisible(true);
    }

    private void showViewOrders() {
        if (viewOrdersView == null) {
            viewOrdersView = new ViewOrdersView();
            setupViewOrdersListeners();
        }
        viewOrdersView.setVisible(true);
    }

    private void setupViewOrdersListeners() {
        viewOrdersView.getSearchButton().addActionListener(e -> {
            String customerName = viewOrdersView.getSearchField().getText();
            ArrayList<OrderData> orders = dbManager.searchOrders(customerName);
            updateOrdersTable(orders);
        });

        viewOrdersView.getBackButton().addActionListener(e -> viewOrdersView.setVisible(false));
    }

    private void updateOrdersTable(ArrayList<OrderData> orders) {
        DefaultTableModel model = viewOrdersView.getTableModel();
        model.setRowCount(0);

        for (OrderData order : orders) {
            model.addRow(new Object[]{
                    order.getOrderId(),
                    order.getCustomerName(),
                    order.getOrderDate(),
                    order.getTotalCost(),
                    order.getDetails()
            });
        }
    }
}