package com.manuela.meucaixa.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class FinancialRecord {

    private Long id;

    private String type;

    private BigDecimal amount;

    private LocalDateTime dueDate;

    private LocalDateTime paymentDate;

    private String description;

    private Category category;

    private Customer customer;
}
