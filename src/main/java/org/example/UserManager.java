package org.example;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class UserManager {
    private static final String FILE_NAME = "Save.txt";
    private static final String FILE_PATH = System.getProperty("user.dir") + File.separator + FILE_NAME;
    private static Map<String, Customer> userCache = new HashMap<>();
    private static final String USER_SECTION_DELIMITER = "==== USER DATA ====";

    // Initialize UserManager and load existing users
    static {
        loadUsers();
    }

    // Save a new user or update existing user
    public static void saveUser(Customer customer) {
        userCache.put(customer.getName(), customer);
        appendUserToFile(customer);
    }

    // Load all users from file
    private static void loadUsers() {
        createFileIfNotExists();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            Customer currentCustomer = null;
            boolean inUserSection = false;

            while ((line = br.readLine()) != null) {
                if (line.equals(USER_SECTION_DELIMITER)) {
                    inUserSection = true;
                    continue;
                }

                if (inUserSection && line.startsWith("Customer: ")) {
                    String name = line.substring(10);
                    currentCustomer = new Customer(name);
                } else if (inUserSection && line.startsWith("VIP Status: ")) {
                    if (currentCustomer != null) {
                        boolean isVIP = Boolean.parseBoolean(line.substring(12));
                        if (isVIP) {
                            currentCustomer.setVIP(true);
                        }
                        userCache.put(currentCustomer.getName(), currentCustomer);
                    }
                } else if (line.startsWith("----------------------------------------")) {
                    inUserSection = false;
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading users: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Append user data to file (do not overwrite)
    private static void appendUserToFile(Customer customer) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_PATH, true))) {
            // Append user section if not present
            if (!isUserSectionPresent()) {
                pw.println(USER_SECTION_DELIMITER);
            }

            pw.println("----------------------------------------");
            pw.println("Customer: " + customer.getName());
            pw.println("VIP Status: " + customer.isVIP());
            pw.println("Registration Date: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            pw.println("----------------------------------------");

        } catch (IOException e) {
            System.err.println("Error appending user to file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Check if user section exists
    private static boolean isUserSectionPresent() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.equals(USER_SECTION_DELIMITER)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Register a new user
    public static Customer registerUser(String name) {
        if (userExists(name)) {
            return userCache.get(name);
        }
        Customer newCustomer = new Customer(name);
        saveUser(newCustomer);
        return newCustomer;
    }

    // Get a user by name
    public static Customer getUser(String name) {
        return userCache.get(name);
    }

    // Check if a user exists
    public static boolean userExists(String name) {
        return userCache.containsKey(name);
    }

    // List all users (for admin)
    public static void displayAllUsers() {
        System.out.println("\nRegistered Users:");
        System.out.println("----------------------------------------");
        for (Customer customer : userCache.values()) {
            System.out.printf("Name: %s (Status: %s)%n",
                    customer.getName(),
                    customer.isVIP() ? "VIP" : "Regular");
        }
        System.out.println("----------------------------------------");
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
                System.out.println("Created new file at: " + FILE_PATH);
            } catch (IOException e) {
                System.err.println("Error creating file: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    // Method to update the VIP status of a user
    public static void updateVIPStatus(String username, boolean isVIP) {
        if (!userExists(username)) {
            System.out.println("User " + username + " not found.");
            return;
        }

        Customer customer = userCache.get(username);
        customer.setVIP(isVIP);  // Update the VIP status

        // Now we need to save the updated user data back to the file
        saveUser(customer);

        System.out.println("Updated VIP status for " + username + " to " + (isVIP ? "VIP" : "Regular"));
    }
}
