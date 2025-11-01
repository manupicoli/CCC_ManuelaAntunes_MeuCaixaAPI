package com.manuela.meucaixa.adapters.outbound.repository;

import com.manuela.meucaixa.adapters.outbound.entities.JpaCustomerEntity;
import com.manuela.meucaixa.domain.customer.Customer;
import com.manuela.meucaixa.domain.customer.CustomerRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class CustomerRepositoryImpl implements CustomerRepository {

    private final JpaCustomerRepository jpaCustomerRepository;

    @Override
    public Customer save(Customer customer) {
        final var customerEntity = getCustomerEntity(customer);
        final var saved = jpaCustomerRepository.save(customerEntity);
        return getCustomer(saved);
    }

    @Override
    public Optional<Customer> findById(Long id) {
        final var customerEntity = jpaCustomerRepository.findById(id);
        return customerEntity.map(CustomerRepositoryImpl::getCustomer);
    }

    @Override
    public List<Customer> findAll() {
        return jpaCustomerRepository.findAll()
            .stream()
            .map(CustomerRepositoryImpl::getCustomer)
            .toList();
    }

    @Override
    public void deleteById(Long id) {
        jpaCustomerRepository.deleteById(id);
    }

    public Optional<Customer> findByCode(String code) {
        final var customerEntity = jpaCustomerRepository.findByCode(code);
        return customerEntity.map(CustomerRepositoryImpl::getCustomer);
    }

    private JpaCustomerEntity getCustomerEntity(Customer customer) {
        return JpaCustomerEntity.builder()
            .id(customer.getId())
            .name(customer.getName())
            .code(customer.getCode())
            .build();
    }

    private static Customer getCustomer(JpaCustomerEntity jpaCustomerEntity) {
        return Customer.builder()
            .id(jpaCustomerEntity.getId())
            .name(jpaCustomerEntity.getName())
            .code(jpaCustomerEntity.getCode())
            .build();
    }
}
