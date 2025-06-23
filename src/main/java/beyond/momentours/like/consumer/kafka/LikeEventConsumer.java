package beyond.momentours.like.consumer.kafka;

import beyond.momentours.like.command.application.service.LikeCommandService;
import beyond.momentours.like.command.domain.dto.LikeEventDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class LikeEventConsumer {

    private final ObjectMapper objectMapper;
    private final LikeCommandService likeCommandService;

    @KafkaListener(topics = "like-events", groupId = "like-consumer")
    public void handleLikeEvent(String message) {
        try {
            LikeEventDto event = objectMapper.readValue(message, LikeEventDto.class);
            log.info("Kafka 이벤트 수신: {}", event);

            likeCommandService.recoverLike(event);

        } catch (Exception e) {
            log.error("Kafka 메시지 처리 실패", e);
        }
    }
}
