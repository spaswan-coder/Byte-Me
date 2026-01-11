package org.example;

import java.awt.*;
import java.util.Map;
import java.util.PriorityQueue;

public class ByteMeGUI {
    private Map<String, FoodItem> menu;
    private PriorityQueue<Order> orders;

    // Constructor to initialize GUI with shared data
    public ByteMeGUI(Map<String, FoodItem> menu, PriorityQueue<Order> orders) {
        this.menu = menu;
        this.orders = orders;
    }

    // Method to launch the GUI
    public void launch() {
        Frame frame = new Frame("Byte Me - GUI");
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        // Create Menu Panel
        Panel menuPanel = createMenuPanel();

        // Create Orders Panel
        Panel ordersPanel = createOrdersPanel();

        // CardLayout to switch between panels
        CardLayout cardLayout = new CardLayout();
        Panel contentPanel = new Panel(cardLayout);
        contentPanel.add(menuPanel, "Menu");
        contentPanel.add(ordersPanel, "Orders");

        // Navigation Panel
        Panel navigationPanel = new Panel();
        Button menuButton = new Button("View Menu");
        Button ordersButton = new Button("Pending Orders");
        navigationPanel.add(menuButton);
        navigationPanel.add(ordersButton);

        frame.add(contentPanel, BorderLayout.CENTER);
        frame.add(navigationPanel, BorderLayout.SOUTH);

        // Button Actions
        menuButton.addActionListener(e -> cardLayout.show(contentPanel, "Menu"));
        ordersButton.addActionListener(e -> cardLayout.show(contentPanel, "Orders"));

        // Close Button Action
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                System.exit(0);
            }
        });

        frame.setVisible(true);
    }

    private Panel createMenuPanel() {
        Panel panel = new Panel();
        panel.setLayout(new BorderLayout());

        List menuList = new List();
        for (Map.Entry<String, FoodItem> entry : menu.entrySet()) {
            FoodItem item = entry.getValue();
            // Include availability in the displayed text
            menuList.add(item.getName() + " - $" + item.getPrice() + " (" + item.getAvailability() + ")");
        }

        panel.add(new Label("Canteen Menu"), BorderLayout.NORTH);
        panel.add(menuList, BorderLayout.CENTER);

        return panel;
    }


    private Panel createOrdersPanel() {
        Panel panel = new Panel();
        panel.setLayout(new BorderLayout());

        List ordersList = new List();
        for (Order order : orders) {
            StringBuilder orderDisplay = new StringBuilder();
            orderDisplay.append("Order ID: ").append(order.getId()).append("\n");
            orderDisplay.append("Items: ");
            for (Map.Entry<FoodItem, Integer> entry : order.getItems().entrySet()) {
                orderDisplay.append(entry.getKey().getName()).append(" (Qty: ").append(entry.getValue()).append("), ");
            }
            // Remove trailing comma and space
            if (orderDisplay.length() > 0) {
                orderDisplay.setLength(orderDisplay.length() - 2);
            }
            orderDisplay.append("\nStatus: ").append(order.getStatus());
            ordersList.add(orderDisplay.toString());
        }

        panel.add(new Label("Pending Orders"), BorderLayout.NORTH);
        panel.add(ordersList, BorderLayout.CENTER);

        return panel;

    }
}