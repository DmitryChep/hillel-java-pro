package ua.ithillel.javapro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ua.ithillel.javapro.model.Order;
import ua.ithillel.javapro.model.Product;
import ua.ithillel.javapro.service.OrderService;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Application.class, args);

        OrderService orderServiceBean = context.getBean(OrderService.class);

        List<Product> products = List.of(   Product.builder()
                .name("iphone")
                .price(1500.0)
                .build(),  Product.builder()
                .price(3000.0)
                .name("MacBook")
                .build());
        Order order = Order.builder()
                .createdAt(LocalDateTime.now())
                .products(products)
                .build();

        orderServiceBean.addOrder(order);
    }
}

