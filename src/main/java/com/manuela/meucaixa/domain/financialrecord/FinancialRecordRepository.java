package com.manuela.meucaixa.domain.financialrecord;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface FinancialRecordRepository {

    FinancialRecord save(FinancialRecord financialRecord);

    Optional<FinancialRecord> findById(Long id);

    void deleteById(Long id);

    Page<FinancialRecord> search(String customerCode, String qs, Pageable pageable);
}
