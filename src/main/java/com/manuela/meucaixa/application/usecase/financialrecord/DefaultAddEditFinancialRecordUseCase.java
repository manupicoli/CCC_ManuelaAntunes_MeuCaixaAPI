package com.manuela.meucaixa.application.usecase.financialrecord;

import com.manuela.meucaixa.application.usecase.DomainException;
import com.manuela.meucaixa.domain.category.Category;
import com.manuela.meucaixa.domain.category.CategoryRepository;
import com.manuela.meucaixa.domain.customer.Customer;
import com.manuela.meucaixa.domain.customer.CustomerRepository;
import com.manuela.meucaixa.domain.financialrecord.FinancialRecord;
import com.manuela.meucaixa.domain.financialrecord.FinancialRecordRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Slf4j
@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class DefaultAddEditFinancialRecordUseCase implements AddEditFinancialRecordUseCase {

    private final FinancialRecordRepository financialRecordRepository;
    private final CustomerRepository customerRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public void execute(final Long id, final AddEditFinancialRecordRequest req) {
        final var record = (id == null) ? createNewOne() : findRequired(id);

        record.setType(req.type());
        record.setAmount(req.amount());
        record.setDueDate(req.dueDate());
        record.setPaymentDate(req.paymentDate());
        record.setDescription(req.description());
        record.setCategory(getCategory(req.category()));
        record.setCustomer(getCustomer(req.customerCode()));

        final var saved = financialRecordRepository.save(record);
        log.info("Financial record={} has been saved successfully for customer={}", saved.getId(), req.customerCode());
    }

    private Customer getCustomer(final String code) {
        return customerRepository.findByCode(code)
            .orElseThrow(() -> new DomainException("Customer with code " + code + " not found"));
    }

    private Category getCategory(final Long id) {
        return categoryRepository.findById(id)
            .orElseThrow(() -> new DomainException("Category with id " + id + " not found"));
    }

    private FinancialRecord createNewOne() {
        return FinancialRecord.builder()
            .notifications(new ArrayList<>())
            .build();
    }

    private FinancialRecord findRequired(final Long id) {
        return financialRecordRepository.findById(id)
            .orElseThrow(() -> new DomainException("Financial record with id " + id + " not found"));
    }
}
