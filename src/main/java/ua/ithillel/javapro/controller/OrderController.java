package ua.ithillel.javapro.controller;


 import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
 import org.springframework.web.bind.annotation.*;
import ua.ithillel.javapro.model.Order;
 import ua.ithillel.javapro.service.OrderServiceImpl;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderServiceImpl orderServiceIml;

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Order> getById (@PathVariable Long id) {
        Optional<Order> order = orderServiceIml.getById(id);
        return order.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<Order>> getAll() {
        List<Order> orders = orderServiceIml.getAll();
        return ResponseEntity.ok(orders);
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<Order> save(@RequestBody Order order) {
        orderServiceIml.save(order);
        return ResponseEntity.ok(order);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Order> delete(@PathVariable Long id) {
        orderServiceIml.delete(id);
        return ResponseEntity.noContent().build();
    }
}
