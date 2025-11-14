package com.manuela.meucaixa.application.usecase.financialrecord;

import com.manuela.meucaixa.auth.CurrentUser;
import com.manuela.meucaixa.domain.financialrecord.FinancialRecord;
import com.manuela.meucaixa.domain.financialrecord.FinancialRecordRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class DefaultListFinancialRecordUseCase implements ListFinancialRecordUseCase {

    private final CurrentUser currentUser;
    private final FinancialRecordRepository financialRecordRepository;

    @Override
    public Page<ListFinancialRecordResponse> execute(final ListFinancialRecordRequest request) {
        return financialRecordRepository.search(currentUser.customerCode(), request.qs(), request.pageable())
            .map(toResponse());
    }

    private static Function<FinancialRecord, ListFinancialRecordResponse> toResponse() {
        return e -> ListFinancialRecordResponse.builder()
            .type(e.getType())
            .amount(e.getAmount())
            .categoryTitle(e.getCategory().getTitle())
            .categoryId(e.getCategory().getId())
            .description(e.getDescription())
            .dueDate(e.getDueDate())
            .paymentDate(e.getPaymentDate())
            .build();
    }
}
