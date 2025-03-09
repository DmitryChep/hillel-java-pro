1. Implement the Order class.This class should have the fields: `id`, `creationDate`, `totalCost`, `products`.

2. Implement the Product class.This class should have the fields: `id`, `name`, `cost`.

3. Orders should be stored in a specialized repository class called **OrderRepository**.

* Implement the method to get an order by id.
* Implement the method to get all orders.
* Implement the method to add a new order.

4. Implement the PingController to check that the program is working.  
This controller should have only one endpoint and return the message "OK".

* The controller is accessible at URL: `http://localhost:8080/ping`.

5. Implement the controller for interacting with orders.

* The controller is accessible at URL: `http://localhost:8080/orders`.
* Get a specific order.
* Get all orders.
* Add a new order.
