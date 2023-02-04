package com.wepat.notification;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotificationDto {
    private String notificationId;
    private String scheduleId;
    private String calendarId;
    private String memberId;
    private String date;
    private boolean read = false;
    private int notificationType;
}
