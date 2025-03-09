package ua.ithillel.javapro.order;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.List;


@Data
@Slf4j
public class CoffeeOrderBoard {

    private final List<Order> orders = new LinkedList<>();
    private int nextOrderNumber = 1;

    public void add(String customerName) {
        Order order = new Order(nextOrderNumber++, customerName);
        orders.add(order);
        log.info("Added new order: Num = {}, Name = {}", order.getOrderNumber(), order.getCustomerName());
    }

    public void deliver() {
        if (!orders.isEmpty()) {
            Order order = orders.removeFirst();
            log.info("Delivered order: Num = {}, Name = {}", order.getOrderNumber(), order.getCustomerName());
        } else {
            log.warn("No orders to deliver.");
        }
    }

    public void deliver(int orderNumber) {
        for (Order order : orders) {
            if (order.getOrderNumber() == orderNumber) {
                orders.remove(order);
                log.info("Delivered order: Num = {}, Name = {}", order.getOrderNumber(), order.getCustomerName());
                return;
            }
        }
        log.error("Order with number {} not found.", orderNumber);
    }

    public void draw() {
        log.info("Current Order Queue:");
        System.out.println("Num | Name");
        for (Order order : orders) {
            System.out.println(order.getOrderNumber() + " | " + order.getCustomerName());
        }
    }
}
