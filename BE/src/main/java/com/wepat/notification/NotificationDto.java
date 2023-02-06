package com.wepat.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDto {
    private String notificationId;
    private String scheduleId;
    private String calendarId;
    private String memberId;
    private String date;
    private boolean read = false;
    private int notificationType;
}
