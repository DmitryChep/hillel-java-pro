package ua.ithillel.javapro.repo;

import org.springframework.stereotype.Repository;
import ua.ithillel.javapro.exception.ExceptionHandler;
import ua.ithillel.javapro.model.Product;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Repository
public class ProductRepository {

    private final List<Product> products = new ArrayList<>();

    public ProductRepository() {
        products.add(new Product(1L, "Product 1", 100.0));
        products.add(new Product(2L, "Product 2", 200.0));
        products.add(new Product(3L, "Product 3", 300.0));
        log.info("ProductRepository initialized with {} products", products.size());
    }

    public List<Product> getAllProducts() {
        log.info("Fetching all products");
        return products;
    }

    public Optional<Product> getProductById(Long id) {
        if (id == 0) {
            log.info("Product is invalid: {} is ", id);
            throw new ExceptionHandler("Product id is invalid");
        }
        log.info("Fetching product with ID: {}", id);
        return products.stream().filter(product -> Objects.equals(product.getId(), id)).findFirst();
    }

    public void addProduct(Product product) {
        if (product == null) {
            log.info("Product is invalid: {} is ", product);
            throw new ExceptionHandler("Product is invalid");
        }
        products.add(product);
        log.info("Product added: {}", product);
    }

    public void removeProduct(Long id) {
        if (id == 0) {
            log.info("Product is invalid: {} is ", id);
            throw new ExceptionHandler("Product id is invalid");
        }
        products.removeIf(product -> Objects.equals(product.getId(), id));
        log.info("Product with ID {} removed", id);
    }
}
