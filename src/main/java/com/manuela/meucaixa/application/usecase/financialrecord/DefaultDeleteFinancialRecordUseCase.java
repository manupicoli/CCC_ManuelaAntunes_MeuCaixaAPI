package com.manuela.meucaixa.application.usecase.financialrecord;

import com.manuela.meucaixa.application.usecase.DomainException;
import com.manuela.meucaixa.domain.financialrecord.FinancialRecordRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class DefaultDeleteFinancialRecordUseCase implements DeleteFinancialRecordUseCase {

    private final FinancialRecordRepository financialRecordRepository;

    @Override
    public void execute(final Long id) {
        final var financialRecord = financialRecordRepository.findById(id)
            .orElseThrow(() -> new DomainException("Financial record not found"));

        financialRecordRepository.deleteById(financialRecord.getId());
        log.info("Financial record successfully deleted");
    }
}
