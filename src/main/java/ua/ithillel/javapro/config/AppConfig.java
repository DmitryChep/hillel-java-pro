package ua.ithillel.javapro.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import org.springframework.context.annotation.Scope;
import ua.ithillel.javapro.repo.ProductRepository;
import ua.ithillel.javapro.service.implementations.CartServiceIml;
import ua.ithillel.javapro.service.implementations.ProductServiceIml;

@Configuration
@ComponentScan(basePackages = "ua.ithillel.javapro")
public class AppConfig {

    @Bean
    public ProductRepository productRepository() {
        return new ProductRepository();
    }

    @Bean
    @Scope("prototype")
    public CartServiceIml cartService(ProductRepository productRepository) {
        return new CartServiceIml(productRepository);
    }

    @Bean
    public ProductServiceIml productService(ProductRepository productRepository) {
        return new ProductServiceIml(productRepository);
    }
}
