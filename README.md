### Hillel Java Pro project
***
### Homework 37.1 Work with Servlet API
***
This project implements a web app with CRUD operations for `Order` and `Product` entities. It uses Apache Tomcat and Jackson for JSON parsing.

__CRUD Operations:__
- **Create (POST)**: Create a new order.
- **Read (GET)**: Get order by ID.
- **Update (PUT)**: Update an order.
- **Delete (DELETE)**: Delete an order by ID.

__API Endpoints__

- **POST /order** - Create an order
- **GET /order/{id}** - Get an order by ID
- **PUT /order/{id}** - Update an order
- **DELETE /order/{id}** - Delete an order

__Tests__

Unit tests are provided with **Mockito** and **JUnit 5**.
