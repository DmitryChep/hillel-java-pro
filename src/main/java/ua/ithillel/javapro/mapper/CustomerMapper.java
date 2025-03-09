package ua.ithillel.javapro.mapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ua.ithillel.javapro.model.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CustomerMapper implements RowMapper<Customer> {

    @Override
    public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
        Customer customer = new Customer();
        customer.setId(rs.getLong("id"));
        customer.setFullName(rs.getString("fullName"));
        customer.setEmail(rs.getString("email"));
        customer.setSocialSecurityNumber(rs.getString("socialSecurityNumber"));
        return customer;
    }
}
