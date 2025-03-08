package ua.ithillel.javapro.service.interfaces;

import ua.ithillel.javapro.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> getAllProducts();

    Optional<Product> getProductById(Long id);

    Product addProduct(Product product);

    void deleteProduct(Long id);
}
