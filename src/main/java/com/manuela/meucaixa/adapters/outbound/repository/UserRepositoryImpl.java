package com.manuela.meucaixa.adapters.outbound.repository;

import com.manuela.meucaixa.adapters.outbound.entities.JpaCustomerEntity;
import com.manuela.meucaixa.adapters.outbound.entities.JpaUserEntity;
import com.manuela.meucaixa.domain.customer.Customer;
import com.manuela.meucaixa.domain.user.Users;
import com.manuela.meucaixa.domain.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final JpaUserRepository jpaUserRepository;

    @Transactional
    @Override
    public Users save(Users user) {
        final var entity = getUserEntity(user);
        final var saved = jpaUserRepository.save(entity);

        return getUser(saved);
    }

    @Transactional
    @Override
    public Users findById(UUID id) {
        return jpaUserRepository.findById(id)
            .map(UserRepositoryImpl::getUser)
            .orElse(null);
    }

    @Transactional
    @Override
    public List<Users> findAll() {
        return jpaUserRepository.findAll()
            .stream()
            .map(UserRepositoryImpl::getUser)
            .toList();
    }

    @Transactional
    @Override
    public void deleteById(UUID id) {
        jpaUserRepository.deleteById(id);
    }

    @Transactional
    @Override
    public Users findByEmail(String email) {
        return jpaUserRepository.findByEmail(email)
            .map(UserRepositoryImpl::getUser)
            .orElse(null);
    }

    private JpaUserEntity getUserEntity(final Users u) {
        return JpaUserEntity.builder()
            .id(u.getId())
            .role(u.getRole())
            .name(u.getName())
            .email(u.getEmail())
            .phone(u.getPhone())
            .customer(getCustomerEntityId(u))
            .build();
    }

    private JpaCustomerEntity getCustomerEntityId(final Users u) {
        if (u.getCustomer() == null) return null;

        return JpaCustomerEntity.builder()
            .id(u.getCustomer().getId())
            .build();
    }

    private static Users getUser(final JpaUserEntity e) {
        return Users.builder()
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
