package ua.ithillel.javapro.service.interfaces;

import ua.ithillel.javapro.model.Product;

import java.util.List;

public interface CartService {
    void addProductToCart(Long productId);

    void removeProductFromCart(Long productId);

    List<Product> getCartItems();

    double getTotalPrice();

}
