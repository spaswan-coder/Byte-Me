package org.example;

import java.util.HashMap;
import java.util.Map;

public class Order implements Comparable<Order> {
    private int id;
    private Map<FoodItem, Integer> items;
    private String status;
    private boolean isVIP;
    private String specialRequest;
    private double totalAmount;
    private boolean isPaid;
    private String upiNumber;
    private String deliveryAddress;

    public Order(int id, Map<FoodItem, Integer> items, boolean isVIP, String specialRequest, String upiNumber, String deliveryAddress) {
        this.id = id;
        this.items = new HashMap<>(items);
        this.status = "Order Received";
        this.isVIP = isVIP;
        this.specialRequest = specialRequest;
        this.totalAmount = calculateTotalAmount();
        this.isPaid = true; // Assume the order is paid when placed
        this.upiNumber = upiNumber;
        this.deliveryAddress = deliveryAddress;
    }

    private double calculateTotalAmount() {
        return items.entrySet().stream().mapToDouble(entry -> entry.getKey().getPrice() * entry.getValue()).sum();
    }

    public int getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Map<FoodItem, Integer> getItems() {
        return items;
    }

    public boolean isVIP() {
        return isVIP;
    }

    public String getSpecialRequest() {
        return specialRequest;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean isPaid) {
        this.isPaid = isPaid;
    }

    public String getUpiNumber() {
        return upiNumber;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    @Override
    public int compareTo(Order other) {
        return Boolean.compare(other.isVIP, this.isVIP);
    }

    @Override
    public String toString() {
        StringBuilder itemDetails = new StringBuilder();
        for (Map.Entry<FoodItem, Integer> entry : items.entrySet()) {
            itemDetails.append(entry.getKey().getName()).append(" x ").append(entry.getValue()).append(", ");
        }
        return "Order #" + id + " - Items: [" + itemDetails.toString() + "] - Status: " + status +
                (isVIP ? " [VIP]" : " [Regular]") +
                (specialRequest.isEmpty() ? "" : " - Special Request: " + specialRequest) +
                "\nUPI Number: " + upiNumber + "\nDelivery Address: " + deliveryAddress;
    }
}