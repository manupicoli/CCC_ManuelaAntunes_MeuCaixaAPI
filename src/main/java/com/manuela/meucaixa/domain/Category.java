package com.manuela.meucaixa.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Category {

    private Long id;

    private String title;

    private String description;

    private Boolean isDefault;

    private Customer customer;
}
