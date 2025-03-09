package ua.ithillel.javapro;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import ua.ithillel.javapro.dao.implementations.CustomerDaoIml;
import ua.ithillel.javapro.model.Customer;

import java.util.List;
import java.util.Optional;

@Configuration
@ComponentScan(basePackages = "ua.ithillel.javapro")
public class Application {
    public static void main(String[] args) {
        try (AnnotationConfigApplicationContext context =
                     new AnnotationConfigApplicationContext(Application.class)) {

            CustomerDaoIml customerDaoIml = context.getBean(CustomerDaoIml.class);

            // Create and save a new Customer
            Customer customer = new Customer(1L, "John Doe", "john@google.com", "123-45-6789");
            customerDaoIml.save(customer);  // Save customer using the DAO

            // Search by ID
            Optional<Customer> foundCustomer = customerDaoIml.getById(1L);
            foundCustomer.ifPresent(System.out::println);

            // Update Customer
            customer.setFullName("John Smith");
            customerDaoIml.update(customer);

            // Get all customers
            List<Customer> allCustomers = customerDaoIml.getAll();
            allCustomers.forEach(System.out::println);

            // Delete the customer
            customerDaoIml.delete(1L);

        } catch (Exception e) {
            throw new RuntimeException("An error occurred", e);
        }
    }
}
