package ua.ithillel.javapro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.ithillel.javapro.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}

