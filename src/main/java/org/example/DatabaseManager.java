package org.example;

import java.io.*;
import java.nio.file.*;
import java.sql.*;
import java.util.ArrayList;

public class DatabaseManager {
    private static String URL;

    static {
        try {
            // Load the SQLite JDBC driver
            Class.forName("org.sqlite.JDBC");

            // Copy the database file from resources to a temporary directory
            Path tempDir = Files.createTempDirectory("sqlite");
            Path tempDb = tempDir.resolve("ordersdb.db");
            try (InputStream is = DatabaseManager.class.getClassLoader().getResourceAsStream("ordersdb.db")) {
                if (is == null) {
                    throw new FileNotFoundException("Database file not found in resources");
                }
                Files.copy(is, tempDb, StandardCopyOption.REPLACE_EXISTING);
            }
            URL = "jdbc:sqlite:" + tempDb.toString();
            System.out.println("Database URL: " + URL);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public DatabaseManager() {
        try (Connection conn = getConnection()) {
            Statement stmt = conn.createStatement();
            // Create orders table
            stmt.execute("CREATE TABLE IF NOT EXISTS orders (" +
                    "order_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "customer_name TEXT NOT NULL," +
                    "order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                    "total REAL NOT NULL)");
            // Create order_details table
            stmt.execute("CREATE TABLE IF NOT EXISTS order_details (" +
                    "detail_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "order_id INTEGER NOT NULL," +
                    "size TEXT NOT NULL," +
                    "crust TEXT NOT NULL," +
                    "toppings TEXT NOT NULL," +
                    "cost REAL NOT NULL," +
                    "FOREIGN KEY(order_id) REFERENCES orders(order_id))");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int saveOrder(String customerName, double totalCost, ArrayList<Pizza> pizzas) {
        int orderId = -1;

        try (Connection conn = getConnection()) {
            conn.setAutoCommit(false);

            try {
                // Insert into orders table
                String orderSql = "INSERT INTO orders (customer_name, total) VALUES (?, ?)";
                PreparedStatement pstmt = conn.prepareStatement(orderSql, Statement.RETURN_GENERATED_KEYS);
                pstmt.setString(1, customerName);
                pstmt.setDouble(2, totalCost);
                pstmt.executeUpdate();

                // Get the generated order_id
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    orderId = rs.getInt(1);
                }

                // Insert each pizza into order_details
                String detailsSql = "INSERT INTO order_details (order_id, size, crust, toppings, cost) " +
                        "VALUES (?, ?, ?, ?, ?)";
                PreparedStatement detailStmt = conn.prepareStatement(detailsSql);

                for (Pizza pizza : pizzas) {
                    detailStmt.setInt(1, orderId);
                    detailStmt.setString(2, pizza.getSize());
                    detailStmt.setString(3, pizza.getCrust());
                    detailStmt.setString(4, String.join(", ", pizza.getToppings()));
                    detailStmt.setDouble(5, pizza.calcCost());
                    detailStmt.executeUpdate();
                }

                conn.commit();
                return orderId;

            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
                return -1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public ArrayList<OrderData> searchOrders(String customerName) {
        ArrayList<OrderData> orders = new ArrayList<>();

        String sql = "SELECT o.order_id, o.customer_name, o.order_date, o.total, " +
                "GROUP_CONCAT(od.size || ' ' || od.crust || ' Pizza with ' || od.toppings) as details " +
                "FROM orders o " +
                "LEFT JOIN order_details od ON o.order_id = od.order_id " +
                "WHERE o.customer_name LIKE ? " +
                "GROUP BY o.order_id, o.customer_name, o.order_date, o.total";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + customerName + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                OrderData order = new OrderData(
                        rs.getInt("order_id"),
                        rs.getString("customer_name"),
                        rs.getTimestamp("order_date"),
                        rs.getDouble("total"),
                        rs.getString("details")
                );
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }
}