package com.manuela.meucaixa.adapters.outbound.repository;

import com.manuela.meucaixa.adapters.outbound.entities.JpaCategoryEntity;
import com.manuela.meucaixa.adapters.outbound.entities.JpaCustomerEntity;
import com.manuela.meucaixa.adapters.outbound.entities.JpaFinancialRecordEntity;
import com.manuela.meucaixa.adapters.outbound.entities.JpaNotificationEntity;
import com.manuela.meucaixa.domain.category.Category;
import com.manuela.meucaixa.domain.customer.Customer;
import com.manuela.meucaixa.domain.financialrecord.FinancialRecord;
import com.manuela.meucaixa.domain.financialrecord.FinancialRecordRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class FinancialRepositoryImpl implements FinancialRecordRepository {

    private final JpaFinancialRecordRepository jpaFinancialRecordRepository;

    @Override
    public FinancialRecord save(FinancialRecord financialRecord) {
        final var financialRecordEntity = getFinancialRecordEntity(financialRecord);
        final var saved = jpaFinancialRecordRepository.save(financialRecordEntity);

        return getFinancialRecord(saved);
    }

    @Override
    public Optional<FinancialRecord> findById(Long id) {
        final var financialRecordEntity = jpaFinancialRecordRepository.findById(id);
        return financialRecordEntity.map(FinancialRepositoryImpl::getFinancialRecord);
    }

    @Override
    public List<FinancialRecord> findAll() {
        return jpaFinancialRecordRepository.findAll()
            .stream()
            .map(FinancialRepositoryImpl::getFinancialRecord)
            .toList();
    }

    @Override
    public void deleteById(Long id) {
        jpaFinancialRecordRepository.deleteById(id);
    }

    private JpaFinancialRecordEntity getFinancialRecordEntity(final FinancialRecord financialRecord) {
        return JpaFinancialRecordEntity.builder()
            .id(financialRecord.getId())
            .type(financialRecord.getType())
            .amount(financialRecord.getAmount())
            .dueDate(financialRecord.getDueDate())
            .paymentDate(financialRecord.getPaymentDate())
            .description(financialRecord.getDescription())
            .category(getJpaCategoryEntityId(financialRecord))
            .customer(getJpaCustomerEntityId(financialRecord))
            .notifications(getJpaNotificationEntities(financialRecord))
        .build();
    }

    private static List<JpaNotificationEntity> getJpaNotificationEntities(FinancialRecord financialRecord) {
        return financialRecord.getNotifications().isEmpty()
            ? new ArrayList<>()
            : financialRecord.getNotifications()
                .stream()
                .map(n -> JpaNotificationEntity.builder()
                    .id(n.getId())
                    .content(n.getContent())
                    .status(n.getStatus())
                    .recipientEmail(n.getRecipientEmail())
                    .scheduledDate(n.getScheduledDate())
                    .financialRecord(JpaFinancialRecordEntity.builder()
                        .id(financialRecord.getId())
                        .build())
                    .build())
            .toList();
    }

    private JpaCustomerEntity getJpaCustomerEntityId(FinancialRecord financialRecord) {
        return JpaCustomerEntity.builder()
            .id(financialRecord.getCustomer().getId())
            .build();
    }

    private JpaCategoryEntity getJpaCategoryEntityId(FinancialRecord financialRecord) {
        return JpaCategoryEntity.builder()
            .id(financialRecord.getCategory().getId())
            .build();
    }

    private static FinancialRecord getFinancialRecord(final JpaFinancialRecordEntity financialRecordEntity) {
        return FinancialRecord.builder()
            .id(financialRecordEntity.getId())
            .type(financialRecordEntity.getType())
            .amount(financialRecordEntity.getAmount())
            .dueDate(financialRecordEntity.getDueDate())
            .paymentDate(financialRecordEntity.getPaymentDate())
            .description(financialRecordEntity.getDescription())
            .category(Category.builder()
                .id(financialRecordEntity.getCategory().getId())
                .build())
            .customer(Customer.builder()
                .id(financialRecordEntity.getCustomer().getId())
                .build())
            .notifications(new ArrayList<>())
            .build();
    }
}
