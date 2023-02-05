package com.wepat.notification.repository;

import com.wepat.notification.NotificationDto;

import java.util.concurrent.ExecutionException;

public interface NotificationRepository {
    String addNotification(NotificationDto notificationDto) throws ExecutionException, InterruptedException;
    void readNotification(String notificationId);
    void deleteNotification(String NotificationId);
    void deleteAll(String memberId);
    void readlAll(String memberId);
    String getScheduleIdByNotificationId(String notificationid) throws ExecutionException, InterruptedException;
}
