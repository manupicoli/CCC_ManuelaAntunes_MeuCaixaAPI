package com.manuela.meucaixa.domain.notification;

import com.manuela.meucaixa.domain.financialrecord.FinancialRecord;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    private Long id;

    private String content;

    private NotificationStatus status;

    private String recipientEmail;

    private LocalDateTime scheduledDate;

    private FinancialRecord financialRecord;

}
