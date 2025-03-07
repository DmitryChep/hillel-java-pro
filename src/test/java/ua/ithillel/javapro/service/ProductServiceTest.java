package ua.ithillel.javapro.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.ithillel.javapro.model.Product;
import org.mockito.Mock;
import ua.ithillel.javapro.repository.ProductRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product(1L, "Product 1", 100.0);
    }

    @Test
    void getProductById_ShouldReturnProduct_WhenIdIsValid() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Optional<Product> result = productService.getProductById(1L);

        assertTrue(result.isPresent());
        assertEquals(product, result.get());
        verify(productRepository).findById(1L);
    }

    @Test
    void getProductById_ShouldThrowException_WhenIdIsNull() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> productService.getProductById(null));

        assertEquals("Product ID cannot be null or 0", thrown.getMessage());
    }

    @Test
    void getProductById_ShouldThrowException_WhenIdIsZero() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> productService.getProductById(0L));

        assertEquals("Product ID cannot be null or 0", thrown.getMessage());
    }

    @Test
    void getProductByName_ShouldReturnProduct_WhenNameIsValid() {
        when(productRepository.findByName("Product 1")).thenReturn(Optional.of(product));

        Optional<Product> result = productService.getProductByName("Product 1");

        assertTrue(result.isPresent());
        assertEquals(product, result.get());
        verify(productRepository).findByName("Product 1");
    }

    @Test
    void getProductByName_ShouldThrowException_WhenNameIsNull() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> productService.getProductByName(null));

        assertEquals("Product name cannot be null or empty", thrown.getMessage());
    }

    @Test
    void getProductByName_ShouldThrowException_WhenNameIsEmpty() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> productService.getProductByName(""));

        assertEquals("Product name cannot be null or empty", thrown.getMessage());
    }

    @Test
    void getAllProducts_ShouldReturnListOfProducts() {
        when(productRepository.findAll()).thenReturn(List.of(product));

        List<Product> result = productService.getAllProducts();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(product, result.get(0));
        verify(productRepository).findAll();
    }

    @Test
    void addProduct_ShouldAddProduct_WhenValid() {
        when(productRepository.save(product)).thenReturn(product);

        Product result = productService.addProduct(product);

        assertEquals(product, result);
        verify(productRepository).save(product);
    }

    @Test
    void addProduct_ShouldThrowException_WhenProductIsNull() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> productService.addProduct(null));

        assertEquals("Product cannot be null", thrown.getMessage());
    }

    @Test
    void addProduct_ShouldThrowException_WhenProductAlreadyExists() {
        when(productRepository.findByName("Product 1")).thenReturn(Optional.of(product));

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> productService.addProduct(product));

        assertEquals("Product already exists", thrown.getMessage());
    }

    @Test
    void deleteProduct_ShouldDeleteProduct_WhenIdIsValid() {
        when(productRepository.existsById(1L)).thenReturn(true);

        productService.deleteProduct(1L);

        verify(productRepository).deleteById(1L);
    }

    @Test
    void deleteProduct_ShouldThrowException_WhenProductDoesNotExist() {
        when(productRepository.existsById(1L)).thenReturn(false);

        NoSuchElementException thrown = assertThrows(NoSuchElementException.class, () -> productService.deleteProduct(1L));

        assertEquals("Product with id 1 not found!", thrown.getMessage());
    }

    @Test
    void deleteProduct_ShouldThrowException_WhenIdIsNull() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> productService.deleteProduct(null));

        assertEquals("Product ID cannot be null or 0", thrown.getMessage());
    }

    @Test
    void deleteProduct_ShouldThrowException_WhenIdIsZero() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> productService.deleteProduct(0L));

        assertEquals("Product ID cannot be null or 0", thrown.getMessage());
    }

    @Test
    void updateProduct_ShouldUpdateProduct_WhenIdIsValid() {
        when(productRepository.existsById(1L)).thenReturn(true);
        when(productRepository.save(product)).thenReturn(product);

        Optional<Product> result = productService.updateProduct(1L, product);

        assertTrue(result.isPresent());
        assertEquals(product, result.get());
        verify(productRepository).save(product);
    }

    @Test
    void updateProduct_ShouldReturnEmpty_WhenProductDoesNotExist() {
        when(productRepository.existsById(1L)).thenReturn(false);

        Optional<Product> result = productService.updateProduct(1L, product);

        assertFalse(result.isPresent());
    }

    @Test
    void updateProduct_ShouldThrowException_WhenIdIsNull() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> productService.updateProduct(null, product));

        assertEquals("Product ID cannot be null or 0", thrown.getMessage());
    }

    @Test
    void updateProduct_ShouldThrowException_WhenProductIsNull() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> productService.updateProduct(1L, null));

        assertEquals("Updated product cannot be null", thrown.getMessage());
    }

    @Test
    void updateProduct_ShouldThrowException_WhenIdIsZero() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> productService.updateProduct(0L, product));

        assertEquals("Product ID cannot be null or 0", thrown.getMessage());
    }
}

