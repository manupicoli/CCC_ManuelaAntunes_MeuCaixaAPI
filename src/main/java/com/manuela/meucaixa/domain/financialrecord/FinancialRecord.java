package com.manuela.meucaixa.domain.financialrecord;

import com.manuela.meucaixa.domain.Notification;
import com.manuela.meucaixa.domain.category.Category;
import com.manuela.meucaixa.domain.customer.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FinancialRecord {

    private Long id;

    private FinancialRecordType type;

    private BigDecimal amount;

    private LocalDateTime dueDate;

    private LocalDateTime paymentDate;

    private String description;

    private Category category;

    private Customer customer;

    private List<Notification>  notifications = new ArrayList<>();
}
