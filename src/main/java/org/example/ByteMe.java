package org.example;

import java.util.*;


public class ByteMe {
    private static Map<String, FoodItem> menu = new TreeMap<>();
    private static PriorityQueue<Order> orders = new PriorityQueue<>();
    private static List<Order> orderHistory = new ArrayList<>();
    private static Map<FoodItem, Integer> cart = new HashMap<>(); // Updated to Map<FoodItem, Integer>
    private static int nextOrderId = 1;

    public static void main(String[] args) {
        initializeMenu();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Welcome to Byte Me!");
            System.out.println("1. Admin Interface");
            System.out.println("2. Customer Interface");
            System.out.println("3. Exit");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    adminInterface();
                    break;
                case 2:
                    customerInterface(scanner);
                    break;
                case 3:
                    System.out.println("Exiting... Thank you for using Byte Me!");
                    ByteMeGUI gui = new ByteMeGUI(menu, orders);
                    gui.launch(); // Launch GUI after exiting CLI
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }





    private static void initializeMenu() {


        menu.put("Pizza", new FoodItem("Pizza", 99, "Fast Food", "Available"));
                menu.put("Salad", new FoodItem("Salad", 49, "Healthy", "Available"));
                menu.put("Steam Momos", new FoodItem("Steam Momos", 60, "Fast Food", "Available"));
                menu.put("Fry Momos", new FoodItem("Fry Momos", 70, "Fast Food", "Out of Stock"));
                menu.put("Tandoori Momos", new FoodItem("Tandoori Momos", 80, "Fast Food", "Available"));
                menu.put("Egg Roll", new FoodItem("Egg Roll", 50, "Fast Food", "Available"));
                menu.put("Veg Roll", new FoodItem("Veg Roll", 45, "Fast Food", "Available"));
                menu.put("Paneer Roll", new FoodItem("Paneer Roll", 55, "Fast Food", "Out of Stock"));
                menu.put("Samosa", new FoodItem("Samosa", 20, "Snacks", "Available"));
                menu.put("Bhel Puri", new FoodItem("Bhel Puri", 35, "Snacks", "Available"));
                menu.put("Tea", new FoodItem("Tea", 15, "Beverage", "Available"));
                menu.put("Coffee", new FoodItem("Coffee", 20, "Beverage", "Available"));
                menu.put("Veg Thali", new FoodItem("Veg Thali", 150, "Meals", "Out of Stock"));
            }


    private static void adminInterface() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Admin Interface");
            System.out.println("1. Menu Management");
            System.out.println("2. Order Management");
            System.out.println("3. Report Generation");
            System.out.println("4. Exit");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    menuManagement();
                    break;
                case 2:
                    orderManagement();
                    break;
                case 3:
                    reportGeneration();
                    System.out.println("1. View All Orders");
                    System.out.println("2. Clear Order History");
                    int historyChoice = scanner.nextInt();
                    switch (historyChoice) {
                        case 1:
                            OrderHistoryManager.displayAllOrders();
                            break;
                        case 2:
                            OrderHistoryManager.clearOrderHistory();
                            break;
                        default:
                            System.out.println("Invalid choice, please try again.");
                            break;
                    }
                    break;
                case 4:
                    System.out.println("Exiting Admin Interface.");
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
                    break;
            }
        }
    }


    private static void customerInterface(Scanner scanner) {
        Customer customer = null;
        while (true) {
            System.out.println("Customer Interface");
            if (customer == null) {
                System.out.println("1. Register as Regular Customer");
            } else {
                System.out.println("Welcome, " + customer.getName() + "!");
                System.out.println("You are currently a " + (customer.isVIP() ? "VIP" : "Regular") + " customer.");
            }
            System.out.println("2. Browse Menu");
            System.out.println("3. Cart Operations");
            System.out.println("4. Order Tracking");
            System.out.println("5. Leave a Review");
            System.out.println("6. View Reviews");
            System.out.println("7. Get VIP Membership");
            System.out.println("8. Exit");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    if (customer == null) {
                        System.out.println("Enter your name to register:");
                        scanner.nextLine(); // Clear the newline character
                        String customerName = scanner.nextLine();

                        // Check if user exists
                        customer = UserManager.getUser(customerName);
                        if (customer == null) {
                            // Register new user
                            customer = UserManager.registerUser(customerName);
                            System.out.println("Welcome new customer: " + customerName);
                        } else {
                            System.out.println("Welcome back, " + customerName +
                                    (customer.isVIP() ? " (VIP Customer)" : ""));
                        }
                    }
                    break;
                case 2:
                    browseMenu();
                    break;
                case 3:
                    if (customer == null) {
                        System.out.println("Please register as a regular customer first.");
                    } else {
                        cartOperations(customer);
                    }
                    break;
                case 4:
                    if (customer != null) {
                        orderTracking(customer);
                        OrderHistoryManager.displayOrderHistory(customer.getName());
                    } else {
                        System.out.println("You need to register as a customer first.");
                    }
                    break;
                case 5:
                    leaveReview(scanner);
                    break;
                case 6:
                    viewReviews(scanner);
                    break;
                case 7:
                    if (customer != null) {
                        System.out.println("Enter amount to pay to become VIP:");
                        double amount = scanner.nextDouble();
                        customer.becomeVIP(amount);
                        // Update user data in file
                        UserManager.updateVIPStatus(customer.getName(), customer.isVIP());
                    } else {
                        System.out.println("You must register as a customer first.");
                    }
                    break;
                case 8:
                    System.out.println("Exiting Customer Interface.");
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
                    break;
            }
        }
    }



    private static void leaveReview(Scanner scanner) {
        System.out.println("Enter food item name to review:");
        scanner.nextLine(); // Consume newline
        String itemName = scanner.nextLine();
        FoodItem item = menu.get(itemName);
        if (item != null) {
            System.out.println("Enter your name:");
            String reviewerName = scanner.nextLine();
            System.out.println("Enter your rating (1-5):");
            int rating = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            System.out.println("Enter your comment:");
            String comment = scanner.nextLine();
            Review review = new Review(reviewerName, rating, comment);
            item.addReview(review);
            System.out.println("Review added for " + itemName);
        } else {
            System.out.println("Food item not found.");
        }
    }

    private static void viewReviews(Scanner scanner) {
        System.out.println("Enter food item name to view reviews:");
        scanner.nextLine(); // Consume newline
        String itemName = scanner.nextLine();
        FoodItem item = menu.get(itemName);
        if (item != null) {
            System.out.println("Reviews for " + itemName + ":");
            List<Review> reviews = item.getReviews();
            if (reviews.isEmpty()) {
                System.out.println("No reviews yet.");
            } else {
                for (Review review : reviews) {
                    System.out.println(review);
                }
            }
        } else {
            System.out.println("Food item not found.");
        }
    }

    private static void menuManagement() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Menu Management:");
            System.out.println("1. Add Food Item");
            System.out.println("2. Update Food Item");
            System.out.println("3. Remove Food Item");
            System.out.println("4. View Menu");
            System.out.println("5. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Enter food item name:");
                    String newItemName = scanner.nextLine();
                    System.out.println("Enter food item price:");
                    double newItemPrice = scanner.nextDouble();
                    scanner.nextLine();
                    System.out.println("Enter food item category:");
                    String newItemCategory = scanner.nextLine();
                    menu.put(newItemName, new FoodItem(newItemName, newItemPrice, newItemCategory, "Available"));
                    System.out.println("Food item added.");
                    break;
                case 2:
                    System.out.println("Enter food item name to update:");
                    String updateItemName = scanner.nextLine();
                    FoodItem itemToUpdate = menu.get(updateItemName);
                    if (itemToUpdate != null) {
                        System.out.println("Enter new price:");
                        double updatedPrice = scanner.nextDouble();
                        scanner.nextLine();
                        System.out.println("Enter new category:");
                        String updatedCategory = scanner.nextLine();
                        menu.put(updateItemName, new FoodItem(updateItemName, updatedPrice, updatedCategory, "Available"));
                        System.out.println("Food item updated.");
                    } else {
                        System.out.println("Food item not found.");
                    }
                    break;
                case 3:
                    System.out.println("Enter food item name to remove:");
                    String removeItemName = scanner.nextLine();
                    if (menu.remove(removeItemName) != null) {
                        System.out.println("Food item removed.");
                    } else {
                        System.out.println("Food item not found.");
                    }
                    break;
                case 4:
                    System.out.println("Current Menu:");
                    for (FoodItem item : menu.values()) {
                        System.out.println(item);
                    }
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }



    private static void orderManagement() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Order Management:");
            System.out.println("1. View Pending Orders");
            System.out.println("2. Update Order Status");
            System.out.println("3. Process Refund");
            System.out.println("4. Exit");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Current Orders:");
                    for (Order order : orders) {
                        System.out.println(order);
                    }
                    break;
                case 2:
                    System.out.println("Enter order ID to update status:");
                    int orderId = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    for (Order order : orders) {
                        if (order.getId() == orderId) {
                            System.out.println("Enter new status:");
                            String newStatus = scanner.nextLine();
                            order.setStatus(newStatus);
                            System.out.println("Order status updated.");
                            break;
                        }
                    }
                    break;
                case 3:
                    System.out.println("Enter Order ID for refund:");
                    int refundOrderId = scanner.nextInt();
                    processRefund(refundOrderId);
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }

    private static void reportGeneration() {
        System.out.println("Order History:");

        double totalSales = 0.0;
        int totalOrders = orderHistory.size();
        Map<String, Integer> itemPopularity = new HashMap<>();

        for (Order order : orderHistory) {
            System.out.println(order);
            System.out.println("Total Price: $" + order.getTotalAmount());

            // Accumulate total sales
            totalSales += order.getTotalAmount();

            // Count item popularity
            for (Map.Entry<FoodItem, Integer> entry : order.getItems().entrySet()) {
                FoodItem item = entry.getKey();
                int quantity = entry.getValue();
                itemPopularity.put(item.getName(), itemPopularity.getOrDefault(item.getName(), 0) + quantity);
            }
        }

        // Display summary
        System.out.println("Total Orders: " + totalOrders);
        System.out.printf("Total Sales: $%.2f\n", totalSales);

        // Find most popular items
        System.out.println("Most Popular Items:");
        if (!itemPopularity.isEmpty()) {
            int maxCount = Collections.max(itemPopularity.values());
            for (Map.Entry<String, Integer> entry : itemPopularity.entrySet()) {
                if (entry.getValue() == maxCount) {
                    System.out.println(entry.getKey() + ": " + entry.getValue() + " times");
                }
            }
        } else {
            System.out.println("No items sold yet.");
        }
    }


    private static void browseMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a search term (or press Enter to view all items):");
        String searchTerm = scanner.nextLine().trim().toLowerCase();

        System.out.println("Menu:");
        boolean found = false;
        for (FoodItem item : menu.values()) {
            if (searchTerm.isEmpty() || item.getName().toLowerCase().contains(searchTerm)) {
                System.out.println(item);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No items found matching your search.");
        }
    }

    private static void cartOperations(Customer customer) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Cart Operations:");
            System.out.println("1. Add Item to Cart with Quantity");
            System.out.println("2. View Cart");
            System.out.println("3. Modify Quantity of Item in Cart");
            System.out.println("4. Remove Item from Cart");
            System.out.println("5. Place Order");
            System.out.println("6. Cancel Order");
            System.out.println("7. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Enter food item name to add:");
                    String itemName = scanner.nextLine();
                    FoodItem item = menu.get(itemName);
                    if (item != null) {
                        if (!item.getStatus().equalsIgnoreCase("Available")) {
                            System.out.println("Sorry, " + itemName + " is currently out of stock.");
                            break;
                        }
                        System.out.println("Enter quantity:");
                        int quantity = scanner.nextInt();
                        if (quantity > 0) {
                            cart.put(item, cart.getOrDefault(item, 0) + quantity);
                            System.out.println("Added " + quantity + " of " + itemName + " to cart.");
                        } else if (quantity == 0) {
                            System.out.println("Quantity cannot be zero. Please enter a valid quantity.");
                        } else {
                            System.out.println("Invalid quantity. Negative values are not allowed.");
                        }

                    } else {
                        System.out.println("Item not found in menu.");
                    }
                    break;

                case 2:
                    System.out.println("Current Cart:");
                    if (cart.isEmpty()) {
                        System.out.println("Your cart is empty.");
                    } else {
                        double totalCartAmount = 0; // To track the total cart amount
                        for (Map.Entry<FoodItem, Integer> entry : cart.entrySet()) {
                            FoodItem cartItem = entry.getKey();
                            int quantity = entry.getValue();
                            double itemTotal = cartItem.getPrice() * quantity; // Calculate item total
                            totalCartAmount += itemTotal; // Add to total cart amount
                            System.out.println(cartItem.getName() + " - Quantity: " + quantity + " - Total: ₹" + itemTotal);
                        }
                        System.out.println("Total Cart Amount: ₹" + totalCartAmount);
                    }
                    break;

                case 3:
                    System.out.println("Enter food item name to modify quantity:");
                    String modifyItemName = scanner.nextLine();
                    FoodItem modifyItem = menu.get(modifyItemName);
                    if (modifyItem != null && cart.containsKey(modifyItem)) {
                        System.out.println("Current quantity: " + cart.get(modifyItem));
                        System.out.println("Enter new quantity:");
                        int newQuantity = scanner.nextInt();
                        if (newQuantity > 0) {
                            cart.put(modifyItem, newQuantity);
                            System.out.println("Updated quantity of " + modifyItemName + " to " + newQuantity + ".");
                        } else {
                            System.out.println("Invalid quantity. Must be greater than zero.");
                        }
                    } else {
                        System.out.println("Item not found in cart.");
                    }
                    break;

                case 4:
                    System.out.println("Enter food item name to remove:");
                    String removeName = scanner.nextLine();
                    FoodItem removeItem = menu.get(removeName);
                    if (removeItem != null && cart.remove(removeItem) != null) {
                        System.out.println(removeName + " removed from cart.");
                    } else {
                        System.out.println("Item not found in cart.");
                    }
                    break;

                case 5:
                    if (cart.isEmpty()) {
                        System.out.println("Your cart is empty. Add items to place an order.");
                    } else {
                        for (FoodItem cartItem : cart.keySet()) {
                            if (!cartItem.getStatus().equalsIgnoreCase("Available")) {
                                System.out.println("Cannot place order. " + cartItem.getName() + " is out of stock.");
                                return; // Exit placing order due to unavailable items.
                            }
                        }
                        placeOrder(customer);
                    }
                    break;

                case 6:
                    cancelOrder(customer);
                    break;

                case 7:
                    return;

                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }



    private static void placeOrder(Customer customer) {
        if (cart.isEmpty()) {
            System.out.println("Cart is empty. Add items to the cart before placing an order.");
            return;
        }

        Scanner scanner = new Scanner(System.in);

        // Print cart details for debugging
        System.out.println("Cart contents:");
        for (Map.Entry<FoodItem, Integer> entry : cart.entrySet()) {
            System.out.println(entry.getKey().getName() + " - Quantity: " + entry.getValue() + " - Price: $" + entry.getKey().getPrice());
        }

        double totalAmount = 0;
        for (Map.Entry<FoodItem, Integer> entry : cart.entrySet()) {
            totalAmount += entry.getKey().getPrice() * entry.getValue();
        }
        System.out.println("Calculated Total Amount: $" + totalAmount);


        System.out.println("Enter any special request for this order:");
        String specialRequest = scanner.nextLine();


        System.out.println("Enter your UPI number for payment:");
        String upiNumber = scanner.nextLine();
        while (!upiNumber.matches("^[\\w.-]+@[\\w]+$")) {  // Simple validation for UPI format
            System.out.println("Invalid UPI format. Please enter a valid UPI number (e.g., yourname@bank):");
            upiNumber = scanner.nextLine();
        }


        System.out.println("Enter your delivery address:");
        String deliveryAddress = scanner.nextLine();


        Order order = new Order(nextOrderId++, new HashMap<>(cart), customer.isVIP(), specialRequest, upiNumber, deliveryAddress);
        orders.add(order);
        orderHistory.add(order);
        System.out.println("Order placed: " + order);
        cart.clear();
        OrderHistoryManager.saveOrder(order, String.valueOf(customer));
    }




    private static void orderTracking(Customer customer) {
        System.out.println("Order Tracking:");
        for (Order order : orderHistory) {
            System.out.println(order);
            System.out.println("Total Price: $" + order.getTotalAmount());
        }
    }



    private static void cancelOrder(Customer customer) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the Order ID to cancel:");
        int orderId = scanner.nextInt();

        Order orderToCancel = null;
        for (Order order : orders) {
            if (order.getId() == orderId && order.getStatus().equals("Order Received")) {
                orderToCancel = order;
                break;
            }
        }

        if (orderToCancel != null) {
            orderToCancel.setStatus("Cancelled");
            orders.remove(orderToCancel);
            System.out.println("Order #" + orderId + " has been canceled.");
        } else {
            System.out.println("Order cannot be canceled. It may not exist or is already processed.");
        }
    }

    private static void processRefund(int orderId) {
        Order orderToRefund = null;
        for (Order order : orderHistory) {
            if (order.getId() == orderId && order.getStatus().equals("Cancelled") && order.isPaid()) {
                orderToRefund = order;
                break;
            }
        }

        if (orderToRefund != null) {
            orderToRefund.setPaid(false); //refunded
            System.out.println("Refund processed for Order #" + orderId + ". Total refunded: $" + orderToRefund.getTotalAmount());
        } else {
            System.out.println("Refund cannot be processed. Order may not be canceled or already refunded.");
        }
    }
}
