package com.wepat.sse;

import com.wepat.notification.NotificationDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Repository
@Slf4j
public class SseEmitterRepository {
    private Map<String, SseEmitter> emitters = new HashMap<>();
    public SseEmitter connect(String id) {
        SseEmitter emitter = new SseEmitter(); // 디폴트 만료 시간 (스프링부트 내장 톰캣 30초 60 * 1000L)
        add(id, emitter);
        try {
            emitter.send(SseEmitter.event()
                    .name("connect") // 해당 이벤트의 이름
                    .data("connected!"));  // 503 에러 방지를 위한 더미
            System.out.println("connect" + id);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return emitter;
    }

    public SseEmitter add(String id, SseEmitter emitter) {
        if (emitters.containsKey(id)) {
            return emitter;
        }
        this.emitters.put(id, emitter);

        emitter.onCompletion(() -> { // emitter를 관리하는 다른 스레드에서 실행한다.
            // thread safe한 자료구조를 사용하지 않으면 concurrentModificationException이 발생할 수 있다.
            // 여기선 thread-safe한 자료구조 CopyOnWriteArrayList를 사용한다.
            this.emitters.remove(id);    // 만료되면 리스트에서 삭제
        });

        emitter.onTimeout(() -> {
            this.emitters.remove(id);
        });

        return emitter;
    }
    public void send(NotificationDto notificationDto) {
        System.out.println(notificationDto.toString());
        try {
            SseEmitter emitter =
                    emitters.get(notificationDto.getMemberId());
            if (emitter == null) {
                System.out.println("sseemitter null");
                return;
            }
            emitter
                    .send(SseEmitter.event()
                    .name("sse")
                    .data(notificationDto));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
    }
}
