package com.manuela.meucaixa.application.usecase.financialrecord;

import com.manuela.meucaixa.application.usecase.NotFoundException;
import com.manuela.meucaixa.domain.financialrecord.FinancialRecordRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class DefaultGetFinancialRecordUseCase implements GetFinancialRecordDetailsUseCase {

    private final FinancialRecordRepository financialRecordRepository;

    @Override
    public GetFinancialRecordDetailsResponse execute(final Long id) {
        final var record = financialRecordRepository.findById(id)
            .orElseThrow(NotFoundException::new);

        return GetFinancialRecordDetailsResponse.builder()
            .type(record.getType())
            .amount(record.getAmount())
            .categoryTitle(record.getCategory().getTitle())
            .description(record.getDescription())
            .dueDate(record.getDueDate())
            .paymentDate(record.getPaymentDate())
            .build();
    }
}
