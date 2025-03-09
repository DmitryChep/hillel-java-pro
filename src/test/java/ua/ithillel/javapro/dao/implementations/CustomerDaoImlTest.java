package ua.ithillel.javapro.dao.implementations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import ua.ithillel.javapro.exception.DaoExceptionHandler;
import ua.ithillel.javapro.mapper.CustomerMapper;
import ua.ithillel.javapro.model.Customer;

import java.util.List;
import java.util.Optional;

public class CustomerDaoImlTest {

    @Mock
    private JdbcTemplate jdbcTemplate;
    @Mock
    private CustomerMapper customerMapper;

    @InjectMocks
    private CustomerDaoIml customerDaoIml;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAll_shouldReturnList_whenCustomersExist() {
        List<Customer> customers = List.of(
                new Customer(1L, "John Doe", "john@google.com", "123-45-6789"),
                new Customer(2L, "Jane Doe", "jane@google.com", "987-65-4321")
        );
        when(jdbcTemplate.query(anyString(), eq(customerMapper))).thenReturn(customers);

        List<Customer> result = customerDaoIml.getAll();

        assertEquals(2, result.size());
        assertEquals("John Doe", result.get(0).getFullName());
        assertEquals("Jane Doe", result.get(1).getFullName());
    }

    @Test
    public void getById_shouldReturnCustomer_whenCustomerExists() throws DaoExceptionHandler {
        Long customerId = 1L;
        Customer customer = new Customer(customerId, "John Doe", "john@google.com", "123-45-6789");

        when(jdbcTemplate.queryForObject(anyString(), eq(customerMapper), eq(customerId))).thenReturn(customer);

        Optional<Customer> result = customerDaoIml.getById(customerId);

        assertTrue(result.isPresent());
        assertEquals("John Doe", result.get().getFullName());
    }

    @Test
    public void getById_shouldReturnEmpty_whenCustomerDoesNotExist() throws DaoExceptionHandler {
        when(jdbcTemplate.queryForObject(anyString(), eq(customerMapper), anyLong()))
                .thenThrow(EmptyResultDataAccessException.class);

        Optional<Customer> result = customerDaoIml.getById(1L);

        assertFalse(result.isPresent());
    }

    @Test
    public void save_shouldSaveCustomer_whenCustomerIsValid() throws DaoExceptionHandler {
        Customer customer = new Customer(null, "John Doe", "john@google.com", "123-45-6789");

        Optional<Customer> result = customerDaoIml.save(customer);

        assertTrue(result.isPresent());
        assertEquals("John Doe", result.get().getFullName());
        verify(jdbcTemplate).update(anyString(), eq(customer.getFullName()), eq(customer.getEmail()), eq(customer.getSocialSecurityNumber()));
    }

    @Test
    public void update_shouldUpdateCustomer_whenCustomerIsValid() throws DaoExceptionHandler {
        Customer customer = new Customer(1L, "John Doe", "john@google.com", "123-45-6789");

        customerDaoIml.update(customer);

        verify(jdbcTemplate).update(anyString(), eq(customer.getFullName()), eq(customer.getEmail()), eq(customer.getSocialSecurityNumber()), eq(customer.getId()));
    }

    @Test
    public void delete_shouldDeleteCustomer_whenIdIsValid() throws DaoExceptionHandler {
        customerDaoIml.delete(1L);

        verify(jdbcTemplate).update(anyString(), eq(1L));
    }

    @Test
    public void getById_shouldThrowDaoException_whenIdIsInvalid() {
        assertThrows(DaoExceptionHandler.class, () -> customerDaoIml.getById(-1L));
    }

    @Test
    public void save_shouldThrowDaoException_whenCustomerIsNull() {
        assertThrows(DaoExceptionHandler.class, () -> customerDaoIml.save(null));
    }

    @Test
    public void update_shouldThrowDaoException_whenCustomerIsInvalid() {
        Customer invalidCustomer = new Customer(null, "John Doe", "john@google.com", "123-45-6789");
        assertThrows(DaoExceptionHandler.class, () -> customerDaoIml.update(invalidCustomer));
    }

    @Test
    public void delete_shouldThrowDaoException_whenIdIsInvalid() {
        assertThrows(DaoExceptionHandler.class, () -> customerDaoIml.delete(-1L));
    }
}
