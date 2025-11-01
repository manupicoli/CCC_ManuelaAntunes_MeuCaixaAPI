package com.manuela.meucaixa.application.usecase.financialrecord;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class DefaultGetFinancialRecordUseCase implements GetFinancialRecordDetailsUseCase {

    @Override
    public GetFinancialRecordDetailsResponse execute(final Long id) {
        return null;
    }
}
