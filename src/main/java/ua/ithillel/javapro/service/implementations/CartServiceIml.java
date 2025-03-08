package ua.ithillel.javapro.service.implementations;

import lombok.Data;
import ua.ithillel.javapro.exception.ExceptionHandler;
import ua.ithillel.javapro.model.Product;
import lombok.extern.slf4j.Slf4j;
import ua.ithillel.javapro.repo.ProductRepository;
import ua.ithillel.javapro.service.interfaces.CartService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Data
public class CartServiceIml implements CartService {
    private final List<Product> cart = new ArrayList<>();
    private final ProductRepository productRepository;

    public void addProductToCart(Long productId) {
        if (productId == null) {
            log.error("Product id is null");
            throw new ExceptionHandler("Product id is null");
        }
        productRepository.getProductById(productId)
                .ifPresent(product -> {
                    cart.add(product);
                    log.info("Added product to cart: {}", product);
                });
    }

    public void removeProductFromCart(Long productId) {
        if (productId == null) {
            log.info("Product ID is invalid: {}", productId);
            throw new ExceptionHandler("Product ID is invalid : " + productId);
        }

        if (cart.isEmpty()) {
            log.info("Remove product from empty cart: {}", productId);
            throw new ExceptionHandler("Remove product from empty cart: " + productId);
        }

        boolean removed = cart.removeIf(product -> Objects.equals(product.getId(), productId));
        if (removed) {
            log.info("Removed product with ID {} from cart", productId);
        } else {
            log.warn("Product with ID {} not found in cart", productId);
            throw new ExceptionHandler("Product with ID " + productId + " not found in cart");
        }
    }

    public List<Product> getCartItems() {
        if (cart.isEmpty()) {
            log.info("Get cart items from empty cart");
            throw new ExceptionHandler("Cart is empty");
        }
        log.info("Fetching items in cart");
        return cart;
    }

    public double getTotalPrice() {
        if (cart.isEmpty()) {
            log.info("Get total price from empty cart");
            throw new ExceptionHandler("Cart is empty");
        }
        double totalPrice = cart.stream().mapToDouble(Product::getPrice).sum();
        log.info("Total price of items in cart: {}", totalPrice);
        return totalPrice;
    }
}
