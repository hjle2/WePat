package com.wepat.notification.controller;

import com.wepat.notification.service.NotificationService;
import com.wepat.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.InvalidParameterException;
import java.util.concurrent.ExecutionException;

@RestController("/notification")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping("/read/{notificationid}")
    public ResponseEntity<?> readNotification(@PathVariable("notificationid") String notificationId) {
        notificationService.readNotification(notificationId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{notificationid}")
    public ResponseEntity<?> deleteNotification(@PathVariable("notificationid") String notificationId) {
        notificationService.deleteNotification(notificationId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<?> deleteAll(HttpServletRequest request) {
        String memberId = JwtUtil.getUserIdByHttpRequest(request);
        notificationService.deleteAll(memberId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/readAll")
    public ResponseEntity<?> readlAll(HttpServletRequest request) {
        String memberId = JwtUtil.getUserIdByHttpRequest(request);
        notificationService.readlAll(memberId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get/{notificationid}")
    public ResponseEntity<?> getScheduleIdByNotificationId(@PathVariable("notificationid") String notificationId) {
        try {
            notificationService.getScheduleIdByNotificationId(notificationId);
        } catch (ExecutionException e) {
            throw new InvalidParameterException();
        } catch (InterruptedException e) {
            throw new InvalidParameterException();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/count")
    public ResponseEntity<?> getScheduleIdByNotificationId(HttpServletRequest request) {
        try {
            String memberId = JwtUtil.getUserIdByHttpRequest(request);
            notificationService.getScheduleIdByNotificationId(memberId);
        } catch (ExecutionException e) {
            throw new InvalidParameterException();
        } catch (InterruptedException e) {
            throw new InvalidParameterException();
        }
        return ResponseEntity.ok().build();
    }
}
