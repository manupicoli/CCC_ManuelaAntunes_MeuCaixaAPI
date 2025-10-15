package com.manuela.meucaixa.domain.user;

import com.manuela.meucaixa.domain.customer.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Users {

    private UUID id;

    private UserRole role;

    private String name;

    private String email;

    private String phone;

    private Customer customer;
}
