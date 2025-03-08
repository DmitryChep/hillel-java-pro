package ua.ithillel.javapro.service.implementations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.ithillel.javapro.model.Product;
import ua.ithillel.javapro.repo.ProductRepository;
import ua.ithillel.javapro.exception.ExceptionHandler;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImlTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceIml productService;

    private Product product1;
    private Product product2;

    @BeforeEach
    void setUp() {
        product1 = new Product(1L, "Product 1", 100.0);
        product2 = new Product(2L, "Product 2", 200.0);
    }

    @Test
    void getAllProducts_shouldReturnList_whenProductsExist() {
        when(productRepository.getAllProducts()).thenReturn(List.of(product1, product2));

        var products = productService.getAllProducts();

        assertNotNull(products);
        assertEquals(2, products.size());
        verify(productRepository, times(1)).getAllProducts();
    }

    @Test
    void getProductById_shouldReturnProduct_whenProductExists() {
        when(productRepository.getProductById(1L)).thenReturn(Optional.of(product1));

        var result = productService.getProductById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        verify(productRepository, times(1)).getProductById(1L);
    }


    @Test
    void getProductById_shouldThrowException_whenInvalidId() {
        ExceptionHandler exception = assertThrows(ExceptionHandler.class, () -> {
            productService.getProductById(-1L);
        });
        assertEquals("Invalid product ID: -1", exception.getMessage());
    }

    @Test
    void addProduct_shouldAddProduct_whenValidProduct() {
        Product product = new Product(1L, "Product 1", 100.0);

        // Setup mock to simulate interaction
        doNothing().when(productRepository).addProduct(product);  // Since it's void, doNothing() is used

        // Call the method under test
        productService.addProduct(product);

        // Verify that the repository method was called with the correct product
        verify(productRepository, times(1)).addProduct(product);
    }


    @Test
    void addProduct_shouldThrowException_whenProductIsNull() {
        ExceptionHandler exception = assertThrows(ExceptionHandler.class, () -> {
            productService.addProduct(null);
        });
        assertEquals("Product is null", exception.getMessage());
    }

    @Test
    void deleteProduct_shouldRemoveProduct_whenProductExists() {
        Product product = new Product(1L, "Product 1", 100.0);
        when(productRepository.getProductById(1L)).thenReturn(Optional.of(product));
        doNothing().when(productRepository).removeProduct(1L);

        productService.deleteProduct(1L);

        verify(productRepository, times(1)).removeProduct(1L);
        verify(productRepository, times(1)).getProductById(1L);
    }

    @Test
    void deleteProduct_shouldThrowException_whenInvalidId() {
        ExceptionHandler exception = assertThrows(ExceptionHandler.class, () -> {
            productService.deleteProduct(-1L);
        });
        assertEquals("Invalid product ID", exception.getMessage());
    }
}
