package org.example;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class OrderHistoryManager {
    private static final String FILE_NAME = "Save.txt";
    private static final String FILE_PATH = System.getProperty("user.dir") + File.separator + FILE_NAME;

    // Save order to file
    public static void saveOrder(Order order, String customerName) {
        createFileIfNotExists();

        try (FileWriter fw = new FileWriter(FILE_PATH, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {

            // Get current timestamp
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String timestamp = dateFormat.format(new Date());

            // Write order header
            out.println("----------------------------------------");
            out.println("Order Date: " + timestamp);
            out.println("Order ID: " + order.getId());
            out.println("Customer Name: " + customerName + (order.isVIP() ? " (VIP)" : " (Regular)"));
            out.println("Delivery Address: " + order.getDeliveryAddress());

            // Write order items
            out.println("Items Ordered:");
            for (Map.Entry<FoodItem, Integer> entry : order.getItems().entrySet()) {
                FoodItem item = entry.getKey();
                int quantity = entry.getValue();
                double itemTotal = item.getPrice() * quantity;
                out.printf("- %s x%d @ $%.2f each = $%.2f%n",
                        item.getName(), quantity, item.getPrice(), itemTotal);
            }

            // Write order summary
            out.printf("Total Amount: $%.2f%n", order.getTotalAmount());
            out.println("Payment Status: " + (order.isPaid() ? "Paid" : "Pending"));
            out.println("UPI Number: " + order.getUpiNumber());

            if (!order.getSpecialRequest().isEmpty()) {
                out.println("Special Request: " + order.getSpecialRequest());
            }

            out.println("Order Status: " + order.getStatus());
            out.println("----------------------------------------\n");

            out.flush();

        } catch (IOException e) {
            System.err.println("Error saving order history: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Helper method to create file if it doesn't exist
    private static void createFileIfNotExists() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            try {
                File directory = file.getParentFile();
                if (directory != null && !directory.exists()) {
                    directory.mkdirs();
                }
                file.createNewFile();
                System.out.println("Created new order history file at: " + FILE_PATH);
            } catch (IOException e) {
                System.err.println("Error creating order history file: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    // Display all orders (for admin)
    public static void displayAllOrders() {
        createFileIfNotExists();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            boolean hasOrders = false;

            while ((line = br.readLine()) != null) {
                System.out.println(line);
                hasOrders = true;
            }

            if (!hasOrders) {
                System.out.println("No orders found in the system.");
            }

        } catch (IOException e) {
            System.err.println("Error reading order history: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Clear all order history (for admin)
    public static void clearOrderHistory() {
        try (PrintWriter pw = new PrintWriter(FILE_PATH)) {
            pw.print("");  // This will overwrite the file with nothing, effectively clearing it
            System.out.println("Order history cleared successfully.");
        } catch (IOException e) {
            System.err.println("Error clearing order history: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Read and display order history for a specific customer
    public static void displayOrderHistory(String customerName) {
        createFileIfNotExists(); // Ensure the file exists
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            boolean isRelevantOrder = false;
            boolean foundOrders = false;

            // Loop through the lines in the order history file
            while ((line = br.readLine()) != null) {
                if (line.startsWith("----------------------------------------")) {
                    isRelevantOrder = false;
                }

                // Check if the customer name matches the one being searched
                if (line.startsWith("Customer Name: ") &&
                        line.substring(14).toLowerCase().startsWith(customerName.toLowerCase())) {
                    isRelevantOrder = true;
                    foundOrders = true;
                }

                // Print the order details if this is the relevant customer's order
                if (isRelevantOrder) {
                    System.out.println(line);
                }
            }

            // If no orders were found for the customer, print a message
            if (!foundOrders) {
                System.out.println("No orders found for customer: " + customerName);
            }

        } catch (IOException e) {
            System.err.println("Error reading order history: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
