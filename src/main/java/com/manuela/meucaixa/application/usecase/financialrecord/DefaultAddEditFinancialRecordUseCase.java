package com.manuela.meucaixa.application.usecase.financialrecord;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class DefaultAddEditFinancialRecordUseCase implements AddEditFinancialRecordUseCase {

    @Override
    public void execute(final Long id, final AddEditFinancialRecordRequest req) {

    }
}
