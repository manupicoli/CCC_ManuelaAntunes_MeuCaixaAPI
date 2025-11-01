package com.manuela.meucaixa.adapters.inbound.controller;

import com.manuela.meucaixa.application.usecase.financialrecord.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class FinancialRecordController implements FinancialRecordControllerApi {

    private final ListFinancialRecordUseCase listFinancialRecordUseCase;
    private final DeleteFinancialRecordUseCase deleteFinancialRecordUseCase;
    private final AddEditFinancialRecordUseCase addEditFinancialRecordUseCase;
    private final GetFinancialRecordDetailsUseCase getFinancialRecordDetailsUseCase;

    @Override
    public void create(final AddEditFinancialRecordRequest request) {
        addEditFinancialRecordUseCase.execute(null, request);
    }

    @Override
    public void update(final Long id, final AddEditFinancialRecordRequest request) {
        addEditFinancialRecordUseCase.execute(id, request);
    }

    @Override
    public void delete(final Long id) {
        deleteFinancialRecordUseCase.execute(id);
    }

    @Override
    public ListFinancialRecordResponse list() {
        return listFinancialRecordUseCase.execute();
    }

    @Override
    public GetFinancialRecordDetailsResponse getDetails(final Long id) {
        return getFinancialRecordDetailsUseCase.execute(id);
    }
}
