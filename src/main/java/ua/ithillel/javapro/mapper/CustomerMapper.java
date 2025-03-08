package com.yourcompany.mapper;

import com.yourcompany.model.Customer;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerMapper implements RowMapper<Customer> {

    @Override
    public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
        Customer customer = new Customer();
        customer.setId(rs.getInt("id"));
        customer.setFullName(rs.getString("fullName"));
        customer.setEmail(rs.getString("email"));
        customer.setSocialSecurityNumber(rs.getString("socialSecurityNumber"));
        return customer;
    }
}
