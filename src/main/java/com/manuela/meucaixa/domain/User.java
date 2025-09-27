package com.manuela.meucaixa.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class User {

    private Long id;

    private String role;

    private String name;

    private String email;

    private String phone;

    private Customer customer;
}
