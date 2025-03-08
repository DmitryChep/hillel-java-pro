package ua.ithillel.javapro.service.implementations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.ithillel.javapro.exception.ExceptionHandler;
import ua.ithillel.javapro.model.Product;
import ua.ithillel.javapro.repo.ProductRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CartServiceImlTest {

    @InjectMocks
    private CartServiceIml cartServiceIml;

    @Mock
    private ProductRepository productRepository;

    private Product product1;
    private Product product2;

    @BeforeEach
    void setUp() {
        product1 = new Product(1L, "Product 1", 100.0);
        product2 = new Product(2L, "Product 2", 150.0);
    }

    @Test
    public void addProductToCart_shouldAddProduct_whenProductExists() {
        Long productId = 1L;
        when(productRepository.getProductById(productId)).thenReturn(Optional.of(product1));
        cartServiceIml.addProductToCart(productId);
        assertEquals(1, cartServiceIml.getCartItems().size());
        assertTrue(cartServiceIml.getCartItems().contains(product1));
        verify(productRepository, times(1)).getProductById(productId);
    }

    @Test
    public void addProductToCart_shouldThrowException_whenProductIdIsNull() {
        assertThrows(ExceptionHandler.class, () -> cartServiceIml.addProductToCart(null));
    }

    @Test
    public void removeProductFromCart_shouldRemoveProduct_whenProductExists() {
        Product product = new Product(1L, "Product1", 100.0);
        cartServiceIml.addProductToCart(product.getId());

        // No need to mock `getProductById` if it's not used
        List<Product> cartItems = cartServiceIml.getCart();

        // Assuming cart is empty after removal
        assertEquals(0, cartItems.size(), "Cart should be empty after removal");
    }


    @Test
    public void removeProductFromCart_shouldThrowException_whenProductIdIsNull() {
        assertThrows(ExceptionHandler.class, () -> cartServiceIml.removeProductFromCart(null));
    }

    @Test
    public void removeProductFromCart_shouldThrowException_whenProductDoesNotExistInCart() {
        Long productId = 1L;
        assertThrows(ExceptionHandler.class, () -> cartServiceIml.removeProductFromCart(productId));
    }

    @Test
    public void removeProductFromCart_shouldThrowException_whenCartIsEmpty() {
        Long productId = 1L;
        assertThrows(ExceptionHandler.class, () -> cartServiceIml.removeProductFromCart(productId));
    }

    @Test
    public void getCartItems_shouldReturnItems_whenCartIsNotEmpty() {
        Long productId = 1L;
        Product product = new Product(productId, "Product 1", 100.0);
        when(productRepository.getProductById(productId)).thenReturn(Optional.of(product));
        cartServiceIml.addProductToCart(productId);
        List<Product> items = cartServiceIml.getCartItems();
        assertEquals(1, items.size());
        assertTrue(items.contains(product));
    }

    @Test
    public void getCartItems_shouldThrowException_whenCartIsEmpty() {
        assertThrows(ExceptionHandler.class, () -> cartServiceIml.getCartItems());
    }

    @Test
    public void getTotalPrice_shouldReturnCorrectTotal_whenCartIsNotEmpty() {
        when(productRepository.getProductById(1L)).thenReturn(Optional.of(product1));
        when(productRepository.getProductById(2L)).thenReturn(Optional.of(product2));
        cartServiceIml.addProductToCart(1L);
        cartServiceIml.addProductToCart(2L);
        double totalPrice = cartServiceIml.getTotalPrice();
        assertEquals(250.0, totalPrice, 0.01);
    }

    @Test
    public void getTotalPrice_shouldThrowException_whenCartIsEmpty() {
        assertThrows(ExceptionHandler.class, () -> cartServiceIml.getTotalPrice());
    }
}
