package ua.ithillel.javapro.dao.implementations;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.ithillel.javapro.dao.interfaces.CustomerDao;
import ua.ithillel.javapro.exception.DaoExceptionHandler;
import ua.ithillel.javapro.mapper.CustomerMapper;
import ua.ithillel.javapro.model.Customer;

import java.util.List;
import java.util.Optional;

@Data
@Slf4j
@AllArgsConstructor
@Repository
public class CustomerDaoIml implements CustomerDao {

    private final JdbcTemplate jdbcTemplate;
    private final CustomerMapper customerMapper;

    @Override
    public List<Customer> getAll() {
        log.info("Fetching all customers");
        List<Customer> customers = jdbcTemplate.query("SELECT * FROM t_customer", customerMapper);
        log.info("Total customers retrieved: {}", customers.size());
        return customers;
    }

    @Override
    public Optional<Customer> getById(Long id) throws DaoExceptionHandler {
        if (id == null || id <= 0) {
            log.warn("Invalid ID provided for getById: {}", id);
            throw new DaoExceptionHandler("Invalid ID provided for getById" + id);
        }

        String sql = "SELECT * FROM t_customer WHERE id = ?";
        try {
            Customer customerById = jdbcTemplate.queryForObject(sql, customerMapper, id);
            log.info("Customer retrieved by ID {}: {}", id, customerById);
            return Optional.of(customerById);
        } catch (EmptyResultDataAccessException e) {
            log.warn("No customer found with ID: {}", id);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> save(Customer customer) throws DaoExceptionHandler {
        if (customer == null) {
            log.warn("Customer is null: {}", customer);
            throw new DaoExceptionHandler("Customer is null" + customer);
        }

        String sql = "INSERT INTO t_customer (fullName, email, socialSecurityNumber) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, customer.getFullName(), customer.getEmail(), customer.getSocialSecurityNumber());
        log.info("Customer saved: {}", customer);
        return Optional.of(customer);
    }

    @Override
    public void update(Customer customer) throws DaoExceptionHandler {
        if (customer == null || customer.getId() == null || customer.getId() <= 0) {
            log.warn("Invalid customer or ID for update: {}", customer);
            throw new DaoExceptionHandler("Invalid customer or ID for update: " + customer);
        }

        String sql = "UPDATE t_customer SET fullName = ?, email = ?, socialSecurityNumber = ? WHERE id = ?";
        jdbcTemplate.update(sql, customer.getFullName(), customer.getEmail(), customer.getSocialSecurityNumber(), customer.getId());
        log.info("Customer updated: {}", customer);
    }

    @Override
    public void delete(Long id) throws DaoExceptionHandler {
        if (id == null || id <= 0) {
            log.warn("Invalid ID for delete: {}", id);
            throw new DaoExceptionHandler("Invalid ID for delete: " + id);
        }

        String sql = "DELETE FROM t_customer WHERE id = ?";
        jdbcTemplate.update(sql, id);
        log.info("Customer with ID {} deleted", id);
    }
}
