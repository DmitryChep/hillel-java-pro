package ua.ithillel.javapro.repo;

import ua.ithillel.javapro.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepo {
    Optional<Order> getById(Long id);
    List<Order> getAll();
    void save(Order order);
    void delete(Long id);


}
