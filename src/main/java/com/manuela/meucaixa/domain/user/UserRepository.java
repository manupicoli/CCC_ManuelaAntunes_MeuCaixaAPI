package com.manuela.meucaixa.domain.user;

import java.util.List;

public interface UserRepository {

    Users save(Users user);

    Users findById(Long id);

    List<Users> findAll();

    void deleteById(Long id);

}
