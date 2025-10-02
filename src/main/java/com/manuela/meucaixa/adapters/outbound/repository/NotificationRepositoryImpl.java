package com.manuela.meucaixa.adapters.outbound.repository;

import com.manuela.meucaixa.adapters.outbound.entities.JpaFinancialRecordEntity;
import com.manuela.meucaixa.adapters.outbound.entities.JpaNotificationEntity;
import com.manuela.meucaixa.domain.financialrecord.FinancialRecord;
import com.manuela.meucaixa.domain.notification.Notification;
import com.manuela.meucaixa.domain.notification.NotificationRepository;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class NotificationRepositoryImpl implements NotificationRepository {

    private JpaNotificationRepository jpaNotificationRepository;

    @Override
    public Notification save(Notification notification) {
        final var entity = getNotificationEntity(notification);
        final var saved = jpaNotificationRepository.save(entity);

        return getNotification(saved);
    }

    @Override
    public Notification findById(Long id) {
        return jpaNotificationRepository.findById(id)
            .map(NotificationRepositoryImpl::getNotification)
            .orElse(null);
    }

    @Override
    public List<Notification> findAll() {
        return jpaNotificationRepository.findAll()
            .stream()
            .map(NotificationRepositoryImpl::getNotification)
            .toList();
    }

    @Override
    public void deleteById(Long id) {
        jpaNotificationRepository.deleteById(id);
    }

    private JpaNotificationEntity getNotificationEntity(final Notification n) {
        return JpaNotificationEntity.builder()
            .id(n.getId())
            .content(n.getContent())
            .status(n.getStatus())
            .recipientEmail(n.getRecipientEmail())
            .scheduledDate(n.getScheduledDate())
            .financialRecord(getFinancialRecordEntityId(n))
            .build();
    }

    private JpaFinancialRecordEntity getFinancialRecordEntityId(final Notification n) {
        if (n.getFinancialRecord() == null) return null;

        return JpaFinancialRecordEntity.builder()
            .id(n.getFinancialRecord().getId())
            .build();
    }

    private static Notification getNotification(final JpaNotificationEntity e) {
        return Notification.builder()
            .id(e.getId())
            .content(e.getContent())
            .status(e.getStatus())
            .recipientEmail(e.getRecipientEmail())
            .scheduledDate(e.getScheduledDate())
            .financialRecord(e.getFinancialRecord() != null
                    ? FinancialRecord.builder()
                    .id(e.getFinancialRecord().getId())
                    .build()
                    : null)
            .build();
    }
}
