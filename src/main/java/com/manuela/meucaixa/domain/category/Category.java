package com.manuela.meucaixa.domain.category;

import com.manuela.meucaixa.domain.customer.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    private Long id;

    private String title;

    private String description;

    private Boolean isDefault;

    private Customer customer;
}
