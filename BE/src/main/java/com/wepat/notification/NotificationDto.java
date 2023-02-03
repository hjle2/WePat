package com.wepat.notification;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotificationDto {
    private String notificationId;
    private String scheduleId;
    private String memberId;
    private String date;
    private boolean read;
    private int notificationType;
}
