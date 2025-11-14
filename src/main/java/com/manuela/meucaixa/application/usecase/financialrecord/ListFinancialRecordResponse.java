package com.manuela.meucaixa.application.usecase.financialrecord;

import com.manuela.meucaixa.domain.financialrecord.FinancialRecordType;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record ListFinancialRecordResponse(Long id,
                                          FinancialRecordType type,
                                          BigDecimal amount,
                                          String categoryTitle,
                                          String description,
                                          LocalDateTime dueDate,
                                          LocalDateTime paymentDate) {
}
