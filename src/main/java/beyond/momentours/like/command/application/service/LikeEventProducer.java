package beyond.momentours.like.command.application.service;

import beyond.momentours.like.command.domain.dto.LikeEventDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeEventProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void sendLikeEvent(LikeEventDto event) {
        try {
            String message = objectMapper.writeValueAsString(event);
            kafkaTemplate.send("like-events", message);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Kafka 메시지 직렬화 실패", e);
        }
    }
}
