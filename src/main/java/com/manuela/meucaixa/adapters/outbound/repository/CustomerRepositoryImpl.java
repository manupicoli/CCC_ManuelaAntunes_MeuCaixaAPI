package com.manuela.meucaixa.adapters.outbound.repository;

import com.manuela.meucaixa.adapters.outbound.entities.JpaCustomerEntity;
import com.manuela.meucaixa.domain.category.Category;
import com.manuela.meucaixa.domain.customer.Customer;
import com.manuela.meucaixa.domain.customer.CustomerRepository;
import com.manuela.meucaixa.domain.user.Users;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class CustomerRepositoryImpl implements CustomerRepository {

    private final JpaCustomerRepository jpaCustomerRepository;

    @Transactional
    @Override
    public Customer save(Customer customer) {
        final var customerEntity = getCustomerEntity(customer);
        final var saved = jpaCustomerRepository.save(customerEntity);
        return getCustomer(saved);
    }

    @Transactional
    @Override
    public Optional<Customer> findById(Long id) {
        final var customerEntity = jpaCustomerRepository.findById(id);
        return customerEntity.map(CustomerRepositoryImpl::getCustomer);
    }

    @Transactional
    @Override
    public List<Customer> findAll() {
        return jpaCustomerRepository.findAll()
            .stream()
            .map(CustomerRepositoryImpl::getCustomer)
            .toList();
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        jpaCustomerRepository.deleteById(id);
    }

    @Transactional
    @Override
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
            .users(getUsers(jpaCustomerEntity))
            .categories(getCategories(jpaCustomerEntity))
            .build();
    }

    private static List<Category> getCategories(JpaCustomerEntity jpaCustomerEntity) {
        return jpaCustomerEntity.getCategories()
            .stream()
            .map(e -> Category.builder()
                .id(e.getId())
                .title(e.getTitle())
                .description(e.getDescription())
                .isDefault(e.getIsDefault())
                .build())
            .toList();
    }

    private static List<Users> getUsers(JpaCustomerEntity jpaCustomerEntity) {
        return jpaCustomerEntity.getUsers()
            .stream()
            .map(e -> Users.builder()
                .id(e.getId())
                .role(e.getRole())
                .name(e.getName())
                .email(e.getEmail())
                .phone(e.getPhone())
                .build())
            .toList();
    }
}
