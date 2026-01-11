package org.example;

import org.example.FoodItem;

import java.util.HashMap;

public class CartOperations {
    private final HashMap<FoodItem, Integer> cart;

    public CartOperations(HashMap<FoodItem, Integer> cart) {
        this.cart = cart;
    }

    public boolean addItemToCart(FoodItem item, int quantity) {
        if (item.getStatus().equalsIgnoreCase("Available") && quantity > 0) {
            cart.put(item, cart.getOrDefault(item, 0) + quantity);
            return true;
        }
        return false;
    }

    public boolean modifyItemQuantity(FoodItem item, int newQuantity) {
        if (newQuantity > 0 && cart.containsKey(item)) {
            cart.put(item, newQuantity);
            return true;
        }
        return false;
    }

    public boolean removeItemFromCart(FoodItem item) {
        return cart.remove(item) != null;
    }

    public double calculateCartTotal() {
        return cart.entrySet().stream()
                .mapToDouble(entry -> entry.getKey().getPrice() * entry.getValue())
                .sum();
    }

    public boolean isCartEmpty() {
        return cart.isEmpty();
    }
}
