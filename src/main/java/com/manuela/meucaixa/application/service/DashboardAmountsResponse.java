package com.manuela.meucaixa.application.service;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record DashboardAmountsResponse(BigDecimal totalAmount,
                                       BigDecimal totalIncome,
                                       BigDecimal totalExpense,
                                       BigDecimal currentMonthIncome,
                                       BigDecimal currentMonthExpense,
                                       Integer totalNextExpense) {
}
