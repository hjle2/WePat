package com.wepat.notification.service;

import com.wepat.notification.NotificationDto;

import java.util.concurrent.ExecutionException;
import java.util.List;

public interface NotificationService {
    void addNotification(String calendarId,
                         String memberId,
                         String scheduleId,
                         String date,
                         int type) throws ExecutionException, InterruptedException;
    void deleteNotification(String NotificationId);
    void deleteAll(String memberId);
    List<NotificationDto> getAllByMemberId(String memberId) throws ExecutionException, InterruptedException;
    NotificationDto getByNotificationId(String notificationId) throws ExecutionException, InterruptedException;

    int getCountByMemberId(String notificationId) throws ExecutionException, InterruptedException;;
    void readAllByMemberId(String memberId) throws ExecutionException, InterruptedException;
}
