package ua.ithillel.javapro.dao.interfaces;

import ua.ithillel.javapro.exception.DaoExceptionHandler;
import ua.ithillel.javapro.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerDao {
    List<Customer> getAll();

    Optional<Customer> getById(Long id) throws DaoExceptionHandler;

    Optional<Customer> save(Customer customer) throws DaoExceptionHandler;

    void update(Customer customer) throws DaoExceptionHandler;

    void delete(Long Id) throws DaoExceptionHandler;


}
