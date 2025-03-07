1. Install and Configure Apache Tomcat
* Download Apache Tomcat from the official website [Tomcat Downloads](https://tomcat.apache.org/download-90.cgi) according to your operating system.
* Extract the archive to the desired folder. 
* Set environment variables (if needed) for Tomcat (e.g., `CATALINA_HOME`).
* To start Tomcat, navigate to the `bin` folder and run the `startup.sh` (for Linux/macOS) or `startup.bat` (for Windows) script.
2. Create a Servlet that Performs CRUD Operations on the Following Entities:
* Implement the `Order` class. This class will store values: `id`, `date`, `cost`, `products`.
* Implement the `Product` class. This class will store values: `id`, `name`, `cost`.

3. What the Servlet should be able to do:
* Create an order with products.
* Get an order by its `id`.
* Update an order.
* Delete an order by `id`.

CRUD - create, read, update, delete. Each servlet method should correspond to one of the CRUD operations:
- **Create** - POST
- **Read** - GET
- **Update** - PUT
- **Delete** - DELETE

**Note!** To parse incoming and outgoing requests in JSON format, you can use the Jackson library.

4. Write Unit Tests for the Servlet Using Mockito + **JUnit 5**.

5. Submit the Assignment via Git:
* Method 1: Create a new repository. In this repository, create a new branch from the `main` branch and switch to it. After completing the task, push to the new branch. Then, open GitHub in your browser and create a pull request. Submit the link to the created pull request for review. 
* Method 2: If you already have a repository for the task and do not plan to create a new one, create a new branch from the `main` branch in this repository, switch to it, complete the task, and then push to the new branch. Then, go to GitHub and create a pull request. Submit the link to the created pull request for review.