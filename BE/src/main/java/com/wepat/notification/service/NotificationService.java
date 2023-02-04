package com.wepat.notification.service;

import com.wepat.dto.ScheduleDto;
import com.wepat.notification.NotificationDto;

import java.util.concurrent.ExecutionException;

public interface NotificationService {
    void addNotification(String calendarId,
                         String memberId,
                         String scheduleId,
                         String date,
                         int type);
    void readNotification(String notificationId);
    void deleteNotification(String NotificationId);
    void deleteAll(String memberId);
    void readlAll(String memberId);
    String getScheduleIdByNotificationId(String notificationId) throws ExecutionException, InterruptedException;
}
