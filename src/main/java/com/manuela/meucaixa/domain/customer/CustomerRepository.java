package com.manuela.meucaixa.domain.customer;
import java.util.List;

public interface CustomerRepository {

    Customer save(Customer customer);

    Customer findById(Long id);

    List<Customer> findAll();

    void deleteById(Long id);

    Customer findByCode(String code);

}
