CREATE TABLE orders (
    order_id INT AUTO_INCREMENT PRIMARY KEY,  -- Unique identifier for each order
    customer_name VARCHAR(100) NOT NULL,      -- Name of the customer placing the order
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  -- Timestamp for when the order is placed
    total DECIMAL(10, 2)  -- Total price for the order
);

-- Create 'order_details' table
CREATE TABLE order_details (
    order_detail_id INT AUTO_INCREMENT PRIMARY KEY,  -- Unique identifier for each order detail record
    order_id INT,  -- Foreign key to the 'orders' table to relate this record to a specific order
    toppings VARCHAR(255),  -- Toppings selected for the pizza
    crust VARCHAR(20),  -- Type of crust selected for the pizza
    size VARCHAR(20),  -- Size of the pizza (e.g., Small, Medium, Large)
    cost DECIMAL(10, 2),  -- Price of a single pizza (used to calculate the total)
    FOREIGN KEY (order_id) REFERENCES orders(order_id)  -- Foreign key relationship to 'orders'
);