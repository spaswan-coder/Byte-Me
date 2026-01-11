import org.example.FoodItem;
import org.example.CartOperations;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class CartOperationsTest {

    private CartOperations cartOperations;
    private HashMap<FoodItem, Integer> cart;
    private HashMap<String, FoodItem> menu;

    @BeforeEach
    void setUp() {
        // Initialize menu
        menu = new HashMap<>();
        menu.put("Pizza", new FoodItem("Pizza", 99, "Fast Food", "Available"));
        menu.put("Fry Momos", new FoodItem("Fry Momos", 70, "Fast Food", "Out of Stock"));
        menu.put("Tea", new FoodItem("Tea", 15, "Beverage", "Available"));
        menu.put("Samosa", new FoodItem("Samosa", 20, "Snacks", "Available"));

        // Initialize cart and operations
        cart = new HashMap<>();
        cartOperations = new CartOperations(cart);
    }

    private FoodItem getMenuItem(String itemName) {
        return menu.get(itemName);
    }

    @Test
    void testOrderingOutOfStockItem() {
        // Arrange
        FoodItem outOfStockItem = getMenuItem("Fry Momos");

        // Act
        boolean result = cartOperations.addItemToCart(outOfStockItem, 2);

        // Assert
        assertFalse(result, "System should prevent ordering out-of-stock items.");
        assertFalse(cart.containsKey(outOfStockItem), "Out-of-stock item should not be added to the cart.");
    }

    @Test
    void testAddingItemToCartUpdatesTotalPrice() {
        // Arrange
        FoodItem tea = getMenuItem("Tea");

        // Act
        boolean result = cartOperations.addItemToCart(tea, 3); // Add 3 teas: 3 * 15 = 45
        double totalPrice = cartOperations.calculateCartTotal();

        // Assert
        assertTrue(result, "Adding item to cart should succeed.");
        assertEquals(3, cart.get(tea), "Quantity in cart should match the added quantity.");
        assertEquals(45.0, totalPrice, 0.01, "Total price should match expected value.");
    }

    @Test
    void testModifyingItemQuantityRecalculatesTotal() {
        // Arrange
        FoodItem samosa = getMenuItem("Samosa");
        cartOperations.addItemToCart(samosa, 5); // Add 5 samosas initially

        // Act
        boolean result = cartOperations.modifyItemQuantity(samosa, 3); // Modify quantity to 3
        double totalPrice = cartOperations.calculateCartTotal();

        // Assert
        assertTrue(result, "Modifying item quantity should succeed.");
        assertEquals(3, cart.get(samosa), "Quantity in cart should match the updated quantity.");
        assertEquals(60.0, totalPrice, 0.01, "Total price should be recalculated correctly.");
    }

    @Test
    void testPreventNegativeItemQuantity() {
        // Arrange
        FoodItem pizza = getMenuItem("Pizza");
        cartOperations.addItemToCart(pizza, 2); // Add 2 pizzas initially

        // Act
        boolean result = cartOperations.modifyItemQuantity(pizza, -1); // Attempt to set quantity to -1

        // Assert
        assertFalse(result, "Setting negative quantity should be prevented.");
        assertEquals(2, cart.get(pizza), "Quantity in cart should remain unchanged.");
    }

    @Test
    void testRemovingItemFromCart() {
        // Arrange
        FoodItem samosa = getMenuItem("Samosa");
        cartOperations.addItemToCart(samosa, 4);

        // Act
        boolean result = cartOperations.removeItemFromCart(samosa);

        // Assert
        assertTrue(result, "Item removal should succeed.");
        assertFalse(cart.containsKey(samosa), "Item should no longer exist in the cart.");
    }

    @Test
    void testCartTotalForEmptyCart() {
        // Act
        double totalPrice = cartOperations.calculateCartTotal();

        // Assert
        assertEquals(0.0, totalPrice, 0.01, "Total price for an empty cart should be zero.");
    }
}
