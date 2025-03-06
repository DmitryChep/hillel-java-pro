package ua.ithillel.javapro.service;

import lombok.Data;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.ithillel.javapro.model.Order;
import ua.ithillel.javapro.repository.OrderRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Data
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;

    public Optional<Order> getOrderById(Long id) {
        if (id == null || id == 0) {
            log.error("Failed to retrieve order: Invalid ID {}", id);
            throw new IllegalArgumentException("Order ID cannot be null or 0");
        }
        log.info("Retrieving order with ID {}", id);
        return orderRepository.findById(id);
    }

    public List<Order> getAllOrders() {
        log.info("Retrieving all orders");
        return orderRepository.findAll();
    }

    public Order addOrder(Order order) {
        if (order == null) {
            log.error("Failed to add order: Invalid order");
            throw new IllegalArgumentException("Order cannot be null");
        }
        log.info("Adding new order: {}", order);
        return orderRepository.save(order);
    }

    public void deleteOrder(Long id) {
        if (id == null || id == 0) {
            log.error("Failed to delete order: Invalid ID {}", id);
            throw new IllegalArgumentException("Order ID cannot be null or 0");
        }
        log.info("Deleting order with ID {}", id);
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
        } else {
            log.error("Order with ID {} does not exist", id);
            throw new NoSuchElementException("Order with ID " + id + " does not exist");
        }
    }

    public Optional<Order> updateOrder(Long id, Order updatedOrder) {
        if (id == null || id == 0) {
            log.error("Failed to update order: Invalid ID {}", id);
            throw new IllegalArgumentException("Order ID cannot be null or 0");
        }
        if (updatedOrder == null) {
            log.error("Failed to update order: Updated order is null");
            throw new IllegalArgumentException("Updated order cannot be null");
        }
        if (orderRepository.existsById(id)) {
            updatedOrder.setId(id);
            log.info("Updating order with ID {}: {}", id, updatedOrder);
            return Optional.of(orderRepository.save(updatedOrder));
        } else {
            log.warn("Order with ID {} not found for update", id);
            return Optional.empty();
        }
    }
}
