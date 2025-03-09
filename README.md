### Hillel Java Pro project
***
### Homework 39.1 Spring MVC practice
***
This is a Spring MVC application for managing orders and products.

**Features**:
- **Order**: Represents an order with `id`, `creationDate`, `totalCost`, and `products`.
- **Product**: Represents a product with `id`, `name`, and `cost`.
- **OrderRepository**: Manages orders, with methods to get orders by `id`, get all orders, and add new orders.
- **Controllers**:
    - **PingController**: Returns "OK" to check if the system is running. (`/ping`)
    - **OrderController**: Manages orders with endpoints to get a specific order, get all orders, and add a new order. (`/orders`)

**Endpoints:**
- `GET /ping` - Health check.
- `GET /orders/{id}` - Get a specific order.
- `GET /orders` - Get all orders.
- `POST /orders` - Add a new order.