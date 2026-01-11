package org.example;

import java.util.ArrayList;
import java.util.List;

public class FoodItem {
    private String name;
    private double price;
    private String category;
    private String availability;  // Added availability field
    private List<Review> reviews;

    // Updated constructor to initialize availability correctly
    public FoodItem(String name, double price, String category, String availability) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.availability = (availability != null) ? availability : "Available"; // Default to "Available"
        this.reviews = new ArrayList<>();
    }

    // Add a default constructor if needed
    public FoodItem() {
        this.reviews = new ArrayList<>();
    }

    // Review management
    public void addReview(Review review) {
        reviews.add(review);
    }

    public List<Review> getReviews() {
        return reviews;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = (availability != null) ? availability : "Available"; // Ensure non-null value
    }

    // Added getStatus() for compatibility
    public String getStatus() {
        return getAvailability(); // Simply return the value of availability
    }

    @Override
    public String toString() {
        return name + " - ₹" + price + " [" + category + "] (" + availability + ")";
    }
}
