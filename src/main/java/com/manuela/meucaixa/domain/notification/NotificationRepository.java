package com.manuela.meucaixa.domain.notification;
import java.util.List;
import java.util.Optional;

public interface NotificationRepository {

    Notification save(Notification notification);

    Optional<Notification> findById(Long id);

    List<Notification> findAll();

    void deleteById(Long id);
}
