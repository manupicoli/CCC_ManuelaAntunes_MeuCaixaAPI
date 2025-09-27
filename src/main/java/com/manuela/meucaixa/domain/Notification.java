package com.manuela.meucaixa.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class Notification {

    private Long id;

    private String content;

    private NotificationStatus status;

    private String recipientEmail;

    private LocalDateTime scheduledDate;

    private FinancialRecord financialRecord;

}
