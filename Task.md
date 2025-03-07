1. Implement the **Order** class. The class should have fields: id, totalCost, products, createdAt.
2. Implement the **Product** class. The class should have fields: id, name, price.
3. Orders should be stored in a specialized repository class called **OrderRepository**.
   * Implement a method to get an order by "id".
   * Implement a method to get all orders.
   * Implement a method to add an order.
   * Implement a method to delete an order.
   * Implement a method to update an order.
4. Set up the Spring application via `application.yml`.
   * Configure the connection to the database.
5. Implement a Ping controller to check if the application is working.
   * This controller should have a single method and return the message "OK".
   * The controller is available at URL: http://localhost:8080/ping.
6. Implement a controller for interacting with the Order resource.
   * The controller is available at URL: http://localhost:8080/orders.
   * Get a specific order.
   * Get all orders.
   * Add a new order.
   * Delete an order.
   * Update an order.