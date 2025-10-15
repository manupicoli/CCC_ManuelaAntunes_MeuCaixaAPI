package com.manuela.meucaixa.application.usecase.user;

public record CreateUserRequest(String firstName,
                                String lastName,
                                String companyName,
                                String email,
                                String phone,
                                String password) {
}
