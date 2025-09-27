package com.manuela.meucaixa.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    private List<Notification>  notifications = new ArrayList<>();
}
