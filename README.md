### Hillel Java Pro project
***
### Homework 41.1 Spring Boot practice
***
#### Order Management System

This project implements a simple Order Management System using Spring Framework. It allows users to manage orders and products with the following features:

- **Order**: Represents an order with fields such as `id`, `totalCost`, `products`, and `createdAt`.
- **Product**: Represents a product with `id`, `name`, and `price`.
- **OrderRepository**: A repository class for storing and managing orders. It provides methods for creating, reading, updating, and deleting orders.
- **Ping Controller**: A simple health check endpoint to verify if the application is running, available at `/ping`.
- **Order Controller**: A REST controller for interacting with orders, including endpoints for getting, adding, updating, and deleting orders.

The application is configured to connect to a database and allows interaction through RESTful APIs.

#### Setup
1. Configure the database connection in `application.yml`.
2. Run the Spring application.
3. Access the API at `http://localhost:8080/orders` for order management and `http://localhost:8080/ping` for the health check.

#### Endpoints
- `GET /ping`: Health check endpoint.
- `GET /orders`: Retrieve all orders.
- `GET /orders/{id}`: Retrieve a specific order by its ID.
- `POST /orders`: Add a new order.
- `DELETE /orders/{id}`: Delete an order by its ID.
- `PUT /orders/{id}`: Update an existing order.

This project provides a basic foundation for an order management system with Spring Boot.