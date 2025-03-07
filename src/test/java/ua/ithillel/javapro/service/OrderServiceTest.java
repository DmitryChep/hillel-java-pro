package ua.ithillel.javapro.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.ithillel.javapro.model.Order;
import ua.ithillel.javapro.model.Product;
import ua.ithillel.javapro.repository.OrderRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    private Order order;
    private Product product1;
    private Product product2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        product1 = new Product(1L, "Product 1", 100.0);
        product2 = new Product(2L, "Product 2", 200.0);
        order = new Order(1L, 0.0, LocalDateTime.now(), List.of(product1, product2));
    }

    @Test
    void getOrderById_ShouldReturnOrder_WhenIdIsValid() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        Optional<Order> result = orderService.getOrderById(1L);

        assertTrue(result.isPresent());
        assertEquals(order, result.get());
        verify(orderRepository).findById(1L);
    }

    @Test
    void getOrderById_ShouldThrowException_WhenIdIsNull() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> orderService.getOrderById(null));

        assertEquals("Order ID cannot be null or 0", thrown.getMessage());
    }

    @Test
    void getOrderById_ShouldThrowException_WhenIdIsZero() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> orderService.getOrderById(0L));

        assertEquals("Order ID cannot be null or 0", thrown.getMessage());
    }

    @Test
    void getAllOrders_ShouldReturnListOfOrders() {
        when(orderRepository.findAll()).thenReturn(List.of(order));

        List<Order> result = orderService.getAllOrders();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(order, result.get(0));
        verify(orderRepository).findAll();
    }

    @Test
    void addOrder_ShouldAddOrder_WhenValid() {
        when(orderRepository.save(order)).thenReturn(order);

        Order result = orderService.addOrder(order);

        assertEquals(order, result);
        verify(orderRepository).save(order);
    }

    @Test
    void addOrder_ShouldThrowException_WhenOrderIsNull() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> orderService.addOrder(null));

        assertEquals("Order cannot be null", thrown.getMessage());
    }

    @Test
    void deleteOrder_ShouldDeleteOrder_WhenIdIsValid() {
        when(orderRepository.existsById(1L)).thenReturn(true);

        orderService.deleteOrder(1L);

        verify(orderRepository).deleteById(1L);
    }

    @Test
    void deleteOrder_ShouldThrowException_WhenOrderDoesNotExist() {
        when(orderRepository.existsById(1L)).thenReturn(false);

        NoSuchElementException thrown = assertThrows(NoSuchElementException.class, () -> orderService.deleteOrder(1L));

        assertEquals("Order with ID 1 does not exist", thrown.getMessage());
    }

    @Test
    void deleteOrder_ShouldThrowException_WhenIdIsNull() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> orderService.deleteOrder(null));

        assertEquals("Order ID cannot be null or 0", thrown.getMessage());
    }

    @Test
    void deleteOrder_ShouldThrowException_WhenIdIsZero() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> orderService.deleteOrder(0L));

        assertEquals("Order ID cannot be null or 0", thrown.getMessage());
    }

    @Test
    void updateOrder_ShouldUpdateOrder_WhenIdIsValid() {
        when(orderRepository.existsById(1L)).thenReturn(true);
        when(orderRepository.save(order)).thenReturn(order);

        Optional<Order> result = orderService.updateOrder(1L, order);

        assertTrue(result.isPresent());
        assertEquals(order, result.get());
        verify(orderRepository).save(order);
    }

    @Test
    void updateOrder_ShouldReturnEmpty_WhenOrderDoesNotExist() {
        when(orderRepository.existsById(1L)).thenReturn(false);

        Optional<Order> result = orderService.updateOrder(1L, order);

        assertFalse(result.isPresent());
    }

    @Test
    void updateOrder_ShouldThrowException_WhenIdIsNull() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> orderService.updateOrder(null, order));

        assertEquals("Order ID cannot be null or 0", thrown.getMessage());
    }

    @Test
    void updateOrder_ShouldThrowException_WhenOrderIsNull() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> orderService.updateOrder(1L, null));

        assertEquals("Updated order cannot be null", thrown.getMessage());
    }

    @Test
    void updateOrder_ShouldThrowException_WhenIdIsZero() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> orderService.updateOrder(0L, order));

        assertEquals("Order ID cannot be null or 0", thrown.getMessage());
    }


    @Test
    void addOrder_ShouldCalculateTotalCost_WhenAddingOrderWithProducts() {
        order.calculateTotalCost();

        assertEquals(300.0, order.getTotalCost());
    }
}
