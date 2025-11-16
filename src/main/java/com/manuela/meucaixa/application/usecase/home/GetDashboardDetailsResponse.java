package com.manuela.meucaixa.application.usecase.home;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;

@Builder
public record GetDashboardDetailsResponse(BigDecimal currentAmount,
                                          BigDecimal totalExpense,
                                          BigDecimal totalIncome,
                                          Integer nextIncomeQuantity,
                                          List<CategorySummaryResponse> categorySummary,
                                          List<MonthlySummaryResponse> monthlySummary) {

    @Builder
    public record CategorySummaryResponse(String category,
                                          BigDecimal total) {
    }

    @Builder
    public record MonthlySummaryResponse(YearMonth month,
                                         BigDecimal totalIncome,
                                         BigDecimal totalExpense) {
    }
}
