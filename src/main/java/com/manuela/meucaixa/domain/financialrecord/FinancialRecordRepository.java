package com.manuela.meucaixa.domain.financialrecord;

import java.util.List;

public interface FinancialRecordRepository {

    FinancialRecord save(FinancialRecord financialRecord);

    FinancialRecord findById(Long id);

    List<FinancialRecord> findAll();

    void deleteById(Long id);

}
