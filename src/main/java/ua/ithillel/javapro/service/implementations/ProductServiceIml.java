package ua.ithillel.javapro.service.implementations;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.ithillel.javapro.model.Product;
import ua.ithillel.javapro.repo.ProductRepository;
import ua.ithillel.javapro.service.interfaces.ProductService;
import ua.ithillel.javapro.exception.ExceptionHandler;

import java.util.List;
import java.util.Optional;

@Slf4j
@Data
@Service
public class ProductServiceIml implements ProductService {

    @Autowired
    private final ProductRepository productRepository;

    public List<Product> getAllProducts() {
        log.info("Fetching all products from the repository");
        return productRepository.getAllProducts();
    }

    public Optional<Product> getProductById(Long id) {
        if (id == null || id <= 0) {
            log.error("Invalid product ID: {}", id);
            throw new ExceptionHandler("Invalid product ID: " + id);
        }

        log.info("Fetching product with ID: {}", id);
        return productRepository.getProductById(id);
    }

    public Product addProduct(Product product) {
        if (product == null) {
            log.error("Product is null");
            throw new ExceptionHandler("Product is null");
        }
        log.info("Adding product: {}", product);
        productRepository.addProduct(product);
        return product;
    }

    public void deleteProduct(Long id) {
        if (id == null || id <= 0) {
            log.error("Invalid product ID: {}", id);
            throw new ExceptionHandler("Invalid product ID");
        }

        log.info("Removing product with ID: {}", id);
        Optional<Product> product = productRepository.getProductById(id);

        productRepository.removeProduct(id);
        log.info("Product with ID {} removed successfully", id);
    }
}
