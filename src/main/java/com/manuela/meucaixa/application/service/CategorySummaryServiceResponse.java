package com.manuela.meucaixa.application.service;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record CategorySummaryServiceResponse(List<CategorySummaryContent> content) {

    @Builder
    public record CategorySummaryContent(String category,
                                         BigDecimal total) {
    }
}
