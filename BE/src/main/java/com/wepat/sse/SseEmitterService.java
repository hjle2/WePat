package com.wepat.sse;

import com.wepat.notification.NotificationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
@RequiredArgsConstructor
public class SseEmitterService {
    private final SseEmitterRepository sseEmitterRepository;
    public SseEmitter connect(String id) {
        return sseEmitterRepository.connect(id);
    }

    public void add(NotificationDto notificationDto) {
        sseEmitterRepository.send(notificationDto);
    }
}
