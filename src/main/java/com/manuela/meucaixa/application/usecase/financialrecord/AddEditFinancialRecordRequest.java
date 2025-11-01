package com.manuela.meucaixa.application.usecase.financialrecord;

import com.manuela.meucaixa.domain.financialrecord.FinancialRecordType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AddEditFinancialRecordRequest(FinancialRecordType type,
                                            BigDecimal amount,
                                            LocalDateTime dueDate,
                                            LocalDateTime paymentDate,
                                            String description,
                                            Long category) {
}
