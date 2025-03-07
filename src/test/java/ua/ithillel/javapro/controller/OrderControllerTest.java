package ua.ithillel.javapro.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ua.ithillel.javapro.model.Order;
import ua.ithillel.javapro.model.Product;
import ua.ithillel.javapro.service.OrderService;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrderService orderService;

    private Order testOrder1;
    private Order testOrder2;
    private Long testOrderId;

    @BeforeEach
    void setUp() {
        testOrder1 = new Order(1L, 250.0, LocalDateTime.now(), List.of(new Product(1L, "Product1", 100.0)));
        testOrder2 = new Order(2L, 350.0, LocalDateTime.now(), List.of(new Product(2L, "Product2", 200.0)));
        testOrderId = 1L;
    }

    @Test
    public void getOrderById_shouldReturnOrder_whenOrderExists() throws Exception {
        when(orderService.getOrderById(testOrderId)).thenReturn(Optional.of(testOrder1));

        mockMvc.perform(get("/orders/{id}", testOrderId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.products[0].name").value("Product1"));

        verify(orderService, times(1)).getOrderById(testOrderId);
    }

    @Test
    public void getOrderById_shouldReturnNotFound_whenOrderDoesNotExist() throws Exception {
        when(orderService.getOrderById(testOrderId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/orders/{id}", testOrderId))
                .andExpect(status().isNotFound());

        verify(orderService, times(1)).getOrderById(testOrderId);
    }

    @Test
    public void getAllOrders_shouldReturnAllOrders_whenOrdersExist() throws Exception {
        List<Order> orderList = Arrays.asList(testOrder1, testOrder2);
        when(orderService.getAllOrders()).thenReturn(orderList);

        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].products[0].name").value("Product1"))
                .andExpect(jsonPath("$[1].products[0].name").value("Product2"));

        verify(orderService, times(1)).getAllOrders();
    }

    @Test
    public void getAllOrders_shouldReturnOk_whenNoOrdersExist() throws Exception {
        when(orderService.getAllOrders()).thenReturn(List.of());

        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());

        verify(orderService, times(1)).getAllOrders();
    }

    @Test
    public void createOrder_shouldReturnCreated_whenOrderIsValid() throws Exception {
        Order order = new Order();
        order.setTotalCost(250.0);
        order.setProducts(List.of(new Product(1L, "Product1", 100.0)));

        when(orderService.addOrder(any(Order.class))).thenReturn(order);

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"price\":250.0, \"products\":[{\"id\":1, \"name\":\"Product1\", \"price\":100.0}]}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.totalCost").value(250.0))
                .andExpect(jsonPath("$.products[0].name").value("Product1"));
    }

    @Test
    public void createOrder_shouldReturnBadRequest_whenOrderIsInvalid() throws Exception {
        String invalidOrderJson = "{\"price\":250.0}";

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidOrderJson))
                .andExpect(status().isCreated());
    }


    @Test
    public void deleteOrder_shouldReturnNoContent_whenOrderExists() throws Exception {
        doNothing().when(orderService).deleteOrder(testOrderId);

        mockMvc.perform(delete("/orders/{id}", testOrderId))
                .andExpect(status().isNoContent());

        verify(orderService, times(1)).deleteOrder(testOrderId);
    }

    @Test
    public void deleteOrder_shouldReturnNotFound_whenOrderDoesNotExist() throws Exception {
        doThrow(new NoSuchElementException()).when(orderService).deleteOrder(testOrderId);

        mockMvc.perform(delete("/orders/{id}", testOrderId))
                .andExpect(status().isNotFound());

        verify(orderService, times(1)).deleteOrder(testOrderId);
    }

    @Test
    public void deleteOrder_shouldReturnBadRequest_whenOrderIdIsInvalid() throws Exception {
        doThrow(new IllegalArgumentException()).when(orderService).deleteOrder(testOrderId);

        mockMvc.perform(delete("/orders/{id}", testOrderId))
                .andExpect(status().isBadRequest());

        verify(orderService, times(1)).deleteOrder(testOrderId);
    }

    @Test
    public void updateOrder_shouldReturnUpdatedOrder_whenOrderExists() throws Exception {
        testOrder1.setTotalCost(250.0);
        Order updatedOrder = new Order(testOrderId, 250.0, LocalDateTime.now(), List.of(new Product(1L, "Product1", 100.0)));

        when(orderService.updateOrder(testOrderId, testOrder1)).thenReturn(Optional.of(updatedOrder));

        mockMvc.perform(put("/orders/{id}", testOrderId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"totalCost\":250.0, \"products\":[{\"id\":1,\"name\":\"Product1\",\"price\":100.0}]}"))
                .andExpect(status().isNotFound());

    }


    @Test
    public void updateOrder_shouldReturnNotFound_whenOrderDoesNotExist() throws Exception {
        when(orderService.updateOrder(testOrderId, testOrder1)).thenReturn(Optional.empty());

        mockMvc.perform(put("/orders/{id}", testOrderId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"price\":250.0, \"products\":[{\"id\":1,\"name\":\"Product1\",\"price\":100.0}]}"))
                .andExpect(status().isNotFound());

    }
}
