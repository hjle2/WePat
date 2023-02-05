package com.wepat.notification.service;

import java.util.concurrent.ExecutionException;

public interface NotificationService {
    void addNotification(String calendarId,
                         String memberId,
                         String scheduleId,
                         String date,
                         int type) throws ExecutionException, InterruptedException;
    void readNotification(String notificationId);
    void deleteNotification(String NotificationId);
    void deleteAll(String memberId);
    void readlAll(String memberId);
    String getScheduleIdByNotificationId(String notificationId) throws ExecutionException, InterruptedException;
}
