package ua.ithillel.javapro.repo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.ithillel.javapro.exception.ExceptionHandler;
import ua.ithillel.javapro.model.Product;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

public class ProductRepositoryTest {

    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository = new ProductRepository();
    }

    @Test
    public void getAllProducts_shouldReturnList_whenProductsExist() {
        List<Product> products = productRepository.getAllProducts();
        assertNotNull(products);
        assertEquals(3, products.size());
    }

    @Test
    public void getProductById_shouldReturnProduct_whenProductExists() {
        Optional<Product> product = productRepository.getProductById(1L);
        assertTrue(product.isPresent());
        assertEquals(1L, product.get().getId());
    }

    @Test
    public void getProductById_shouldReturnEmpty_whenProductNotFound() {
        Optional<Product> product = productRepository.getProductById(999L);
        assertFalse(product.isPresent());
    }

    @Test
    public void addProduct_shouldAddProductToList() {
        Product newProduct = new Product(4L, "Product 4", 400.0);
        productRepository.addProduct(newProduct);

        List<Product> products = productRepository.getAllProducts();
        assertEquals(4, products.size());
        assertTrue(products.contains(newProduct));
    }

    @Test
    public void removeProduct_shouldRemoveProductFromList() {
        productRepository.removeProduct(1L);

        List<Product> products = productRepository.getAllProducts();
        assertEquals(2, products.size());
        assertFalse(products.stream().anyMatch(p -> p.getId().equals(1L)));
    }

    @Test
    public void removeProduct_shouldThrowException_whenInvalidId() {
        Exception exception = assertThrows(ExceptionHandler.class, () -> productRepository.removeProduct(0L));
        assertEquals("Product id is invalid", exception.getMessage());
    }
}
