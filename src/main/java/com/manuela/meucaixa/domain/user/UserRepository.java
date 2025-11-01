package com.manuela.meucaixa.domain.user;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {

    Users save(Users user);

    Optional<Users> findById(UUID id);

    List<Users> findAll();

    void deleteById(UUID id);

    Users findByEmail(String email);

}
