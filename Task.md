1. Create a new package `coffee.order`.

2. Create a class `CoffeeOrderBoard`. This class is analogous to a queue of orders in a coffee shop. This class contains a list of orders.

3. Create a class `Order`. The class is essentially an order. It contains the order number and the name of the person who made the order.

4. Implement the `add` method in the `CoffeeOrderBoard` class. This method adds a new order and assigns it a number (natural order).  
5. Natural order means that if the last order number is 86, the next one will be 87.  
6. There should never be a situation where the order number repeats within a single `CoffeeOrderBoard` object.

7. Implement the `deliver` method in the `CoffeeOrderBoard` class. This method gives out the next order in the queue. Delivery is accompanied by the removal of the order from the list.

8. Overload the `deliver` method in the `CoffeeOrderBoard` class, passing the order number as an argument. This method gives out the order with the given number. Delivery is accompanied by the removal of the order from the list. This method handles situations where an order placed later is ready earlier.

9. Implement the `draw` method in the `CoffeeOrderBoard` class. This method prints the current state of the queue to the console, in the order of the next order to be delivered.

* For example:
```
Num | Name
4   | Alen
27  | Yoda
33  | Obi-van
34  | John Snow
```
Where `Num` is the order number and `Name` is the name of the customer.

10. Enable logging - use Logback or Log4j.  
11. Configure logging to print information to the console.  
12. Choose the most appropriate log levels for different operations in the program.  
13. Logging should reflect the flow of the operation and contain enough information for operation analysis.  
14. If an exception is thrown, the stack trace should be logged at the `error` level.
