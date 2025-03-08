package ua.ithillel.javapro;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ua.ithillel.javapro.config.AppConfig;
import ua.ithillel.javapro.service.implementations.CartServiceIml;

public class Application {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        CartServiceIml cartServiceIml = context.getBean(CartServiceIml.class);

        cartServiceIml.addProductToCart(1L);
        cartServiceIml.addProductToCart(2L);
        cartServiceIml.addProductToCart(3L);

        System.out.println("Products in the cart after adding: " + cartServiceIml.getCartItems());

        cartServiceIml.removeProductFromCart(2L);
        System.out.println("Products in the cart after removing product with id 2: " + cartServiceIml.getCartItems());

        CartServiceIml newCartServiceIml = context.getBean(CartServiceIml.class);
        newCartServiceIml.addProductToCart(2L);

        System.out.println("Products in the new cart: " + newCartServiceIml.getCartItems());
    }
}
