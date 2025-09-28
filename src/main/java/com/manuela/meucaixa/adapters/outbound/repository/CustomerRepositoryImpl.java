package com.manuela.meucaixa.adapters.outbound.repository;

import com.manuela.meucaixa.adapters.outbound.entities.JpaCustomerEntity;
import com.manuela.meucaixa.domain.User;
import com.manuela.meucaixa.domain.category.Category;
import com.manuela.meucaixa.domain.customer.Customer;
import com.manuela.meucaixa.domain.customer.CustomerRepository;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class CustomerRepositoryImpl implements CustomerRepository {

    private final JpaCustomerRepository jpaCustomerRepository;

    @Override
    public Customer save(Customer customer) {
        final var customerEntity = getCustomerEntity(customer);
        final var saved = jpaCustomerRepository.save(customerEntity);
        return getCustomer(saved);
    }

    @Override
    public Customer findById(Long id) {
        final var customerEntity = jpaCustomerRepository.findById(id);
        return customerEntity.map(CustomerRepositoryImpl::getCustomer).orElse(null);
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
            .users(getUsers(jpaCustomerEntity))
            .categories(getCategories(jpaCustomerEntity))
            .build();
    }

    private static List<Category> getCategories(final JpaCustomerEntity customer) {
        return customer.getCategories()
            .stream()
            .map(c -> Category.builder()
                .id(c.getId())
                .title(c.getTitle())
                .description(c.getDescription())
                .isDefault(c.getIsDefault())
                .build())
            .toList();
    }

    private static List<User> getUsers(final JpaCustomerEntity customer) {
        return customer.getUsers()
            .stream()
            .map(c -> User.builder()
                .id(c.getId())
                .name(c.getName())
                .role(c.getRole())
                .email(c.getEmail())
                .phone(c.getPhone())
                .build())
            .toList();
    }
}
