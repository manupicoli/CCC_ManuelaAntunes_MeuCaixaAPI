package com.manuela.meucaixa.domain.notification;
import java.util.List;

public interface NotificationRepository {

    Notification save(Notification notification);

    Notification findById(Long id);

    List<Notification> findAll();

    void deleteById(Long id);
}
