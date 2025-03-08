1. Implement the **Product** class, which has the following fields: **id, name, price**.
2. Products are stored in a **ProductRepository** class as a list. This list is initialized with data when the application starts.
3. **ProductRepository** allows performing CRUD operations on **Product** objects.
4. Implement the **Cart** class, where products can be added and removed by **id**.
5. Implement a console application for managing the cart:
    * Adding a product to the cart
    * Removing a product from the cart
6. Every time a cart instance is requested from the application context, a new cart instance should be returned.
