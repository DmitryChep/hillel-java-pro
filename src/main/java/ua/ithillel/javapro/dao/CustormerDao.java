package com.yourcompany.dao;

import com.yourcompany.mapper.CustomerMapper;
import com.yourcompany.model.Customer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomerDao {

    private final JdbcTemplate jdbcTemplate;

    public CustomerDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Додавання кастомера
    public void addCustomer(Customer customer) {
        String sql = "INSERT INTO Customer (fullName, email, socialSecurityNumber) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, customer.getFullName(), customer.getEmail(), customer.getSocialSecurityNumber());
    }

    // Пошук кастомера по id
    public Customer getCustomerById(int id) {
        String sql = "SELECT * FROM Customer WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new CustomerMapper(), id);
    }

    // Оновлення кастомера
    public void updateCustomer(Customer customer) {
        String sql = "UPDATE Customer SET fullName = ?, email = ?, socialSecurityNumber = ? WHERE id = ?";
        jdbcTemplate.update(sql, customer.getFullName(), customer.getEmail(), customer.getSocialSecurityNumber(), customer.getId());
    }

    // Видалення кастомера
    public void deleteCustomer(int id) {
        String sql = "DELETE FROM Customer WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    // Отримання всіх кастомерів
    public List<Customer> getAllCustomers() {
        String sql = "SELECT * FROM Customer";
        return jdbcTemplate.query(sql, new CustomerMapper());
    }
}
