package com.manuela.meucaixa.adapters.outbound.repository;

import com.manuela.meucaixa.adapters.outbound.entities.JpaCustomerEntity;
import com.manuela.meucaixa.adapters.outbound.entities.JpaUserEntity;
import com.manuela.meucaixa.domain.customer.Customer;
import com.manuela.meucaixa.domain.user.User;
import com.manuela.meucaixa.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final JpaUserRepository jpaUserRepository;

    @Override
    public User save(User user) {
        final var entity = getUserEntity(user);
        final var saved = jpaUserRepository.save(entity);

        return getUser(saved);
    }

    @Override
    public User findById(Long id) {
        return jpaUserRepository.findById(id)
            .map(UserRepositoryImpl::getUser)
            .orElse(null);
    }

    @Override
    public List<User> findAll() {
        return jpaUserRepository.findAll()
            .stream()
            .map(UserRepositoryImpl::getUser)
            .toList();
    }

    @Override
    public void deleteById(Long id) {
        jpaUserRepository.deleteById(id);
    }

    private JpaUserEntity getUserEntity(final User u) {
        return JpaUserEntity.builder()
            .id(u.getId())
            .role(u.getRole())
            .name(u.getName())
            .email(u.getEmail())
            .phone(u.getPhone())
            .customer(getCustomerEntityId(u))
            .build();
    }

    private JpaCustomerEntity getCustomerEntityId(final User u) {
        if (u.getCustomer() == null) return null;

        return JpaCustomerEntity.builder()
            .id(u.getCustomer().getId())
            .build();
    }

    private static User getUser(final JpaUserEntity e) {
        return User.builder()
            .id(e.getId())
            .role(e.getRole())
            .name(e.getName())
            .email(e.getEmail())
            .phone(e.getPhone())
            .customer(e.getCustomer() != null
                    ? Customer.builder()
                    .id(e.getCustomer().getId())
                    .name(e.getCustomer().getName())
                    .code(e.getCustomer().getCode())
                    .build()
                    : null)
            .build();
    }
}
