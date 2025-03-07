package ua.ithillel.javapro.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.ithillel.javapro.model.Order;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;

import static org.mockito.Mockito.*;

public class OrderServletTest {

    @InjectMocks
    private OrderServlet orderServlet;

    @Mock
    private HttpServletRequest mockRequest;

    @Mock
    private HttpServletResponse mockResponse;

    @Mock
    private StringWriter stringWriter;

    @Mock
    private PrintWriter printWriter;

    private Order order;

    @BeforeEach
    public void setup() throws IOException {
        MockitoAnnotations.openMocks(this);

        when(mockResponse.getWriter()).thenReturn(printWriter);

        order = new Order();
        order.setId(1);
        order.setDate("2025-03-07");
        order.setCost(100.0);
        order.setProducts(new java.util.ArrayList<>());

        // Initialize OrderServlet with some data
        orderServlet.setOrderMap(new HashMap<>());
        orderServlet.getOrderMap().put(order.getId(), order);
    }

    @Test
    public void createOrder_shouldReturnCreatedStatus_whenOrderIsCreated() throws Exception {
        String orderJson = "{\"date\":\"2025-03-07\",\"cost\":100.0,\"products\":[]}";
        when(mockRequest.getReader()).thenReturn(new java.io.BufferedReader(new java.io.StringReader(orderJson)));

        orderServlet.doPost(mockRequest, mockResponse);

        verify(mockResponse).setStatus(HttpServletResponse.SC_CREATED);
        verify(mockResponse.getWriter()).write(contains("Order created with ID"));
    }

    @Test
    public void findOrder_shouldReturnOrder_whenOrderExists() throws Exception {
        when(mockRequest.getPathInfo()).thenReturn("/" + order.getId());

        orderServlet.doGet(mockRequest, mockResponse);

        verify(mockResponse).setStatus(HttpServletResponse.SC_OK);
        verify(mockResponse.getWriter()).write(contains("\"id\":" + order.getId()));
    }

    @Test
    public void findOrder_shouldReturnNotFound_whenOrderDoesNotExist() throws Exception {
        int orderId = 999;
        when(mockRequest.getPathInfo()).thenReturn("/" + orderId);

        orderServlet.doGet(mockRequest, mockResponse);

        verify(mockResponse).setStatus(HttpServletResponse.SC_NOT_FOUND);
        verify(mockResponse.getWriter()).write("Order not found");
    }

    @Test
    public void updateOrder_shouldReturnOkStatus_whenOrderIsUpdated() throws Exception {
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
    public void deleteOrder_shouldReturnOkStatus_whenOrderIsDeleted() throws Exception {
        when(mockRequest.getPathInfo()).thenReturn("/" + order.getId());

        orderServlet.doDelete(mockRequest, mockResponse);

        verify(mockResponse).setStatus(HttpServletResponse.SC_OK);
        verify(mockResponse.getWriter()).write("Order deleted");

        assert orderServlet.getOrderMap().get(order.getId()) == null;
    }

    @Test
    public void deleteOrder_shouldReturnNotFound_whenOrderDoesNotExist() throws Exception {
        int orderId = 999;
        when(mockRequest.getPathInfo()).thenReturn("/" + orderId);

        orderServlet.doDelete(mockRequest, mockResponse);

        verify(mockResponse).setStatus(HttpServletResponse.SC_NOT_FOUND);
        verify(mockResponse.getWriter()).write("Order not found");
    }
}
