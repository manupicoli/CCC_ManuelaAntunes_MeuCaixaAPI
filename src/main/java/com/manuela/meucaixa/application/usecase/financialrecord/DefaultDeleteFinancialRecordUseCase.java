package com.manuela.meucaixa.application.usecase.financialrecord;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class DefaultDeleteFinancialRecordUseCase implements DeleteFinancialRecordUseCase {

    @Override
    public void execute(final Long id) {

    }
}
