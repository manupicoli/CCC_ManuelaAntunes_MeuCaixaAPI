package com.manuela.meucaixa.application.usecase.home;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record GetDashboardDetailsResponse(BigDecimal currentAmount,
                                          BigDecimal totalExpense,
                                          BigDecimal totalIncome,
                                          Integer nextIncomeQuantity) {
}
