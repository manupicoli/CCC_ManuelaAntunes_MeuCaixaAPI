package com.manuela.meucaixa.domain.customer;
import java.util.List;
import java.util.Optional;

public interface CustomerRepository {

    Customer save(Customer customer);

    Optional<Customer> findById(Long id);

    List<Customer> findAll();

    void deleteById(Long id);

    Optional<Customer> findByCode(String code);

}
