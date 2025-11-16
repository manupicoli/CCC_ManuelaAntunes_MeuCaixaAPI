package com.manuela.meucaixa.application.service;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record CalculateDashboardAmountsResponse(BigDecimal totalAmount,
                                                BigDecimal totalIncome,
                                                BigDecimal totalExpense,
                                                BigDecimal currentMonthIncome,
                                                BigDecimal currentMonthExpense,
                                                Integer totalNextExpense) {
}
