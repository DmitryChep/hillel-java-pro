package ua.ithillel.javapro.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.ithillel.javapro.model.Product;
import ua.ithillel.javapro.repository.ProductRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    public Optional<Product> getProductById(Long id) {
        if (id == null || id == 0) {
            log.error("Failed to retrieve product: Invalid ID {}", id);
            throw new IllegalArgumentException("Product ID cannot be null or 0");
        }
        log.info("Retrieving product with ID {}", id);
        return productRepository.findById(id);
    }

    public Optional<Product> getProductByName(String name) {
        if (name == null || name.isEmpty()) {
            log.error("Failed to retrieve product: Invalid name {}", name);
            throw new IllegalArgumentException("Product name cannot be null or empty");
        }
        log.info("Retrieving product with name {}", name);
        return productRepository.findByName(name);
    }

    public List<Product> getAllProducts() {
        log.info("Retrieving all products");
        return productRepository.findAll();
    }

    public Product addProduct(Product product) {
        if (product == null) {
            log.error("Failed to add product: Invalid product");
            throw new IllegalArgumentException("Product cannot be null");
        }
        if (getProductByName(product.getName()).isPresent()) {
            log.error("Failed to add product with name {} already exists", product.getName());
            throw new IllegalArgumentException("Product already exists");
        }
        log.info("Adding new product: {}", product);
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        if (id == null || id == 0) {
            log.error("Failed to delete product: Invalid ID {}", id);
            throw new IllegalArgumentException("Product ID cannot be null or 0");
        }
        if (productRepository.existsById(id)) {
            log.info("Deleting product with ID {}", id);
            productRepository.deleteById(id);
        } else {
            log.warn("Product with ID {} not found for deletion", id);
            throw new NoSuchElementException("Product with id " + id + " not found!");
        }
    }

    public Optional<Product> updateProduct(Long id, Product updatedProduct) {
        if (id == null || id == 0) {
            log.error("Failed to update product: Invalid ID {}", id);
            throw new IllegalArgumentException("Product ID cannot be null or 0");
        }
        if (updatedProduct == null) {
            log.error("Failed to update product: Updated product is null");
            throw new IllegalArgumentException("Updated product cannot be null");
        }
        if (productRepository.existsById(id)) {
            updatedProduct.setId(id);
            log.info("Updating product with ID {}: {}", id, updatedProduct);
            return Optional.of(productRepository.save(updatedProduct));
        } else {
            log.warn("Product with ID {} not found for update", id);
            return Optional.empty();
        }
    }
}
