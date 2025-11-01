package com.manuela.meucaixa.domain.financialrecord;

import java.util.List;
import java.util.Optional;

public interface FinancialRecordRepository {

    FinancialRecord save(FinancialRecord financialRecord);

    Optional<FinancialRecord> findById(Long id);

    List<FinancialRecord> findAll();

    void deleteById(Long id);

}
