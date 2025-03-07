package ua.ithillel.javapro.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.ithillel.javapro.model.Order;

import java.util.HashMap;

import static org.mockito.Mockito.*;

public class OrderServletTest {

    private OrderServlet orderServlet;
    private HttpServletRequest mockRequest;
    private HttpServletResponse mockResponse;
    private Order order;

    @BeforeEach
    public void setup() {
        orderServlet = new OrderServlet();
        ObjectMapper objectMapper = new ObjectMapper();
        mockRequest = mock(HttpServletRequest.class);
        mockResponse = mock(HttpServletResponse.class);

        order = new Order();
        order.setId(1);
        order.setDate("2025-03-07");
        order.setCost(100.0);
        order.setProducts(new java.util.ArrayList<>());

        orderServlet.setOrderMap(new HashMap<>());
        orderServlet.getOrderMap().put(order.getId(), order);
    }

    @Test
    public void testDoPost_ShouldCreateOrder() throws Exception {
        String orderJson = "{\"date\":\"2025-03-07\",\"cost\":100.0,\"products\":[]}";
        when(mockRequest.getReader()).thenReturn(new java.io.BufferedReader(new java.io.StringReader(orderJson)));

        orderServlet.doPost(mockRequest, mockResponse);

        verify(mockResponse).setStatus(HttpServletResponse.SC_CREATED);
        verify(mockResponse.getWriter()).write(contains("Order created with ID"));
    }

    @Test
    public void testDoGet_ShouldReturnOrder() throws Exception {
        when(mockRequest.getPathInfo()).thenReturn("/" + order.getId());

        orderServlet.doGet(mockRequest, mockResponse);

        verify(mockResponse).setStatus(HttpServletResponse.SC_OK);
        verify(mockResponse.getWriter()).write(contains("\"id\":" + order.getId()));
    }

    @Test
    public void testDoGet_ShouldReturnNotFound() throws Exception {
        int orderId = 999;
        when(mockRequest.getPathInfo()).thenReturn("/" + orderId);

        orderServlet.doGet(mockRequest, mockResponse);

        verify(mockResponse).setStatus(HttpServletResponse.SC_NOT_FOUND);
        verify(mockResponse.getWriter()).write("Order not found");
    }

    @Test
    public void testDoPut_ShouldUpdateOrder() throws Exception {
        String updatedOrderJson = "{\"date\":\"2025-03-07\",\"cost\":200.0,\"products\":[]}";
        when(mockRequest.getReader()).thenReturn(new java.io.BufferedReader(new java.io.StringReader(updatedOrderJson)));
        when(mockRequest.getPathInfo()).thenReturn("/" + order.getId());

        orderServlet.doPut(mockRequest, mockResponse);

        verify(mockResponse).setStatus(HttpServletResponse.SC_OK);
        verify(mockResponse.getWriter()).write("Order updated");

        Order updatedOrder = orderServlet.getOrderMap().get(order.getId());
        assert updatedOrder.getCost() == 200.0;
    }

    @Test
    public void testDoDelete_ShouldDeleteOrder() throws Exception {
        when(mockRequest.getPathInfo()).thenReturn("/" + order.getId());

        orderServlet.doDelete(mockRequest, mockResponse);

        verify(mockResponse).setStatus(HttpServletResponse.SC_OK);
        verify(mockResponse.getWriter()).write("Order deleted");

        assert orderServlet.getOrderMap().get(order.getId()) == null;
    }

    @Test
    public void testDoDelete_ShouldReturnNotFound() throws Exception {
        int orderId = 999;
        when(mockRequest.getPathInfo()).thenReturn("/" + orderId);

        orderServlet.doDelete(mockRequest, mockResponse);

        verify(mockResponse).setStatus(HttpServletResponse.SC_NOT_FOUND);
        verify(mockResponse.getWriter()).write("Order not found");
    }
}
