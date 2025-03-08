package com.yourcompany.controller;

import com.yourcompany.dao.CustomerDao;
import com.yourcompany.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerDao customerDao;

    @Autowired
    public CustomerController(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    // Додавання нового кастомера
    @PostMapping
    public String addCustomer(@RequestBody Customer customer) {
        customerDao.addCustomer(customer);
        return "redirect:/customers";
    }

    // Отримання всіх кастомерів
    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerDao.getAllCustomers();
    }

    // Пошук кастомера за ID
    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable("id") int id) {
        return customerDao.getCustomerById(id);
    }

    // Оновлення кастомера
    @PutMapping("/{id}")
    public String updateCustomer(@PathVariable("id") int id, @RequestBody Customer customer) {
        customer.setId(id);
        customerDao.updateCustomer(customer);
        return "redirect:/customers";
    }

    // Видалення кастомера
    @DeleteMapping("/{id}")
    public String deleteCustomer(@PathVariable("id") int id) {
        customerDao.deleteCustomer(id);
        return "redirect:/customers";
    }
}

