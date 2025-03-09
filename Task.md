1. Implement the **Customer** class, which consists of: id, fullName, email, and socialSecurityNumber.
2. Implement the **CustomerDao**, which should have the following operations: add, search by id, update, delete, and retrieve all customers. This class interacts with the database.
3. Create the **Customer** table - this can be done manually or via **JdbcTemplate**.
4. Configure **JdbcTemplate** for working with the database. You can choose either **PostgreSQL** or **MySQL**.
5. Create mappers to convert records from the database into Java objects.