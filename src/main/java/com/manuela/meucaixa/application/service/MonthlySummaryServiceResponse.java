package com.manuela.meucaixa.application.service;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;

@Builder
public record MonthlySummaryServiceResponse(List<MonthlySummaryContent> content) {

    @Builder
    public record MonthlySummaryContent(YearMonth month,
                                        BigDecimal totalIncome,
                                        BigDecimal totalExpense) {
    }
}
