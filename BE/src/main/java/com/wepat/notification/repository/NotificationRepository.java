package com.wepat.notification.repository;

import com.wepat.notification.NotificationDto;

import java.util.concurrent.ExecutionException;
import java.util.List;

public interface NotificationRepository {
    String addNotification(NotificationDto notificationDto) throws ExecutionException, InterruptedException;
    void deleteNotification(String NotificationId);
    void deleteAll(String memberId);
    List<NotificationDto> getAllByMemberId(String memberId) throws ExecutionException, InterruptedException;
    NotificationDto getByNotificationId(String notificationId) throws ExecutionException, InterruptedException;
    int getCountByMemberId(String memberId) throws ExecutionException, InterruptedException;
    void readAllByMemberId(String memberId) throws ExecutionException, InterruptedException;
}
