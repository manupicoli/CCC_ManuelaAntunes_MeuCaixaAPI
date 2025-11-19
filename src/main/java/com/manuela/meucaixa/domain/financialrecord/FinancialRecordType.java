package com.manuela.meucaixa.domain.financialrecord;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FinancialRecordType {
    INCOME("Entrada"),
    EXPENSE("Saída");

    private final String value;
}
