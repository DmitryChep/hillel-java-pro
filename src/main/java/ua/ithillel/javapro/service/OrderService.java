package ua.ithillel.javapro.service;

import ua.ithillel.javapro.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    Optional<Order> getById(Long id);
    List<Order> getAll();
    void save(Order order);
    void delete(Long id);
}
