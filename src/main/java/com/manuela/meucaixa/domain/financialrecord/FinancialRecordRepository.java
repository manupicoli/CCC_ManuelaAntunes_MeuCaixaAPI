package com.manuela.meucaixa.domain.financialrecord;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface FinancialRecordRepository {

    FinancialRecord save(FinancialRecord financialRecord);

    Optional<FinancialRecord> findByIdAndCustomerCode(Long id, String customerCode);

    void deleteById(Long id);

    Page<FinancialRecord> search(String customerCode, String qs, Pageable pageable);

    List<FinancialRecord> findAllByCustomerCode(String customerCode);

    List<FinancialRecord> filterByPeriod(LocalDate start, LocalDate end);
}
