package com.manuela.meucaixa.application.usecase.financialrecord;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class DefaultListFinancialRecordUseCase implements ListFinancialRecordUseCase {

    @Override
    public ListFinancialRecordResponse execute() {
        return null;
    }
}
