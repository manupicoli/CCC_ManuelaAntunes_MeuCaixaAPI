package com.manuela.meucaixa.application.usecase.financialrecord;

import org.springframework.data.domain.Pageable;

public record ListFinancialRecordRequest(String qs,
                                         Pageable pageable) {
}
