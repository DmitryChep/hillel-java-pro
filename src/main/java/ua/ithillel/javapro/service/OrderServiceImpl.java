package ua.ithillel.javapro.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.ithillel.javapro.model.Order;
import ua.ithillel.javapro.repo.OrderRepoImpl;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepoImpl orderRepoImp;

    @Override
    public Optional<Order> getById(Long id) {
        if (id == null || id == 0) {
            throw new IllegalArgumentException("Invalid order ID");
        }
        return orderRepoImp.getById(id);
    }

    @Override
    public List<Order> getAll() {
        List<Order> orders = orderRepoImp.getAll();
        return orders;
    }

    @Override
    public void save(Order order) {
        if(order == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }
        orderRepoImp.save(order);

    }

    @Override
    public void delete(Long id) {
        if (id == null || id == 0) {
            throw new IllegalArgumentException("Invalid oder ID");
        }
        Order order = orderRepoImp.getById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found for ID: " + id));
        orderRepoImp.delete(id);
    }
}
