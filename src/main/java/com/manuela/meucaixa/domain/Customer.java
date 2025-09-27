package com.manuela.meucaixa.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Customer {

    private Long id;

    private String name;

    private String code;
}
