package com.manuela.meucaixa.application.usecase.user;

public record UpdateUserRequest(String firstName,
                                String lastName,
                                String companyName,
                                String phone) {
}
