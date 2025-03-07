package ua.ithillel.javapro.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import ua.ithillel.javapro.model.Order;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@WebServlet("/orders")
public class OrderServlet extends HttpServlet {
    private Map<Integer, Order> orderMap;
    private static int currentId = 1;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws SecurityException, IOException {
        Order order = objectMapper.readValue(request.getReader(), Order.class);
        order.setId(currentId++);
        orderMap.put(order.getId(), order);

        log.info("Created new order with ID: {}", order.getId());

        response.setStatus(HttpServletResponse.SC_CREATED);
        response.getWriter().write("Order created with ID: " + order.getId());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws SecurityException, IOException {
        int orderId = Integer.parseInt(request.getPathInfo().substring(1));
        Order order = orderMap.get(orderId);

        if (order != null) {
            log.info("Retrieved order with ID: {}", orderId);
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(objectMapper.writeValueAsString(order));
        } else {
            log.warn("Order with ID: {} not found", orderId);
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("Order not found");
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws SecurityException, IOException {
        int orderId = Integer.parseInt(request.getPathInfo().substring(1));
        Order order = objectMapper.readValue(request.getReader(), Order.class);

        if (orderMap.containsKey(orderId)) {
            order.setId(orderId);
            orderMap.put(orderId, order);

            log.info("Updated order with ID: {}", orderId);
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("Order updated");
        } else {
            log.warn("Order with ID: {} not found for update", orderId);
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("Order not found");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws SecurityException, IOException {
        int orderId = Integer.parseInt(request.getPathInfo().substring(1));
        if (orderMap.remove(orderId) != null) {
            log.info("Deleted order with ID: {}", orderId);
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("Order deleted");
        } else {
            log.warn("Order with ID: {} not found for deletion", orderId);
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("Order not found");
        }
    }
}
