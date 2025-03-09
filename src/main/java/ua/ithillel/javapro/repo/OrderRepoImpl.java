package ua.ithillel.javapro.repo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;
import ua.ithillel.javapro.model.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Repository
public class OrderRepoImpl implements OrderRepo {

    private List<Order> orders;

    public Optional<Order> getById(Long id) {
        return orders.stream().filter(order -> order.getId() == id).findFirst();
    }

    public List<Order> getAll() {
        return orders;
    }

    public void save(Order order) {
        orders.add(order);
    }

    public void delete(Long id) {
        orders.removeIf(order -> order.getId() == id);
    }
}

