package com.manuela.meucaixa.adapters.outbound.repository;

import com.manuela.meucaixa.adapters.outbound.entities.JpaCategoryEntity;
import com.manuela.meucaixa.adapters.outbound.entities.JpaCustomerEntity;
import com.manuela.meucaixa.adapters.outbound.entities.JpaFinancialRecordEntity;
import com.manuela.meucaixa.adapters.outbound.entities.JpaNotificationEntity;
import com.manuela.meucaixa.domain.category.Category;
import com.manuela.meucaixa.domain.customer.Customer;
import com.manuela.meucaixa.domain.financialrecord.FinancialRecord;
import com.manuela.meucaixa.domain.financialrecord.FinancialRecordRepository;
import com.manuela.meucaixa.domain.notification.Notification;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class FinancialRepositoryImpl implements FinancialRecordRepository {

    private final JpaFinancialRecordRepository jpaFinancialRecordRepository;

    @Transactional
    @Override
    public FinancialRecord save(FinancialRecord financialRecord) {
        final var financialRecordEntity = getFinancialRecordEntity(financialRecord);
        final var saved = jpaFinancialRecordRepository.save(financialRecordEntity);

        return getFinancialRecord(saved);
    }

    @Transactional
    @Override
    public Optional<FinancialRecord> findByIdAndCustomerCode(Long id, String customerCode) {
        final var financialRecordEntity = jpaFinancialRecordRepository.findByIdAndCustomerCode(id, customerCode);
        return financialRecordEntity.map(FinancialRepositoryImpl::getFinancialRecord);
    }

    @Transactional
    @Override
    public Page<FinancialRecord> search(String customerCode, String qs, Pageable pageable) {
        return jpaFinancialRecordRepository.search(customerCode, qs, pageable)
            .map(FinancialRepositoryImpl::getFinancialRecord);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        jpaFinancialRecordRepository.deleteById(id);
    }

    @Transactional
    @Override
    public List<FinancialRecord> findAllByCustomerCode(String customerCode) {
        return jpaFinancialRecordRepository.findAllByCustomerCode(customerCode)
            .stream()
            .map(FinancialRepositoryImpl::getFinancialRecord)
            .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public List<FinancialRecord> filterByPeriod(final LocalDate start, final LocalDate end, final String customerCode) {
        final var startDateTime = start.atStartOfDay();
        final var endDateTime = end.atTime(23, 59, 59);

        return jpaFinancialRecordRepository.filterByPeriodAndCustomerCode(startDateTime, endDateTime, customerCode)
            .stream()
            .map(FinancialRepositoryImpl::getFinancialRecord)
            .toList();
    }

    @Transactional
    @Override
    public List<FinancialRecord> findByCategoryIdAndCode(Long categoryId, String code) {
        return jpaFinancialRecordRepository.findByCategoryIdAndCustomerCode(categoryId, code)
            .stream()
            .map(FinancialRepositoryImpl::getFinancialRecord)
            .toList() ;
    }

    @Override
    public Boolean existsByCategoryIdAndCustomerCode(Long categoryId, String customerCode) {
        return jpaFinancialRecordRepository.existsByCategoryIdAndCustomerCode(categoryId, customerCode);
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
                .title(financialRecordEntity.getCategory().getTitle())
                .description(financialRecordEntity.getCategory().getDescription())
                .isDefault(financialRecordEntity.getCategory().getIsDefault())
                .build())
            .customer(Customer.builder()
                .id(financialRecordEntity.getCustomer().getId())
                .name(financialRecordEntity.getCustomer().getName())
                .code(financialRecordEntity.getCustomer().getCode())
                .build())
            .notifications(financialRecordEntity.getNotifications().isEmpty()
                ? new ArrayList<>()
                : financialRecordEntity.getNotifications().stream()
                .map(n -> Notification.builder()
                    .id(n.getId())
                    .content(n.getContent())
                    .status(n.getStatus())
                    .recipientEmail(n.getRecipientEmail())
                    .scheduledDate(n.getScheduledDate())
                    .build())
                .toList())
            .build();
    }
}
