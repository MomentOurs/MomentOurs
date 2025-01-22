package beyond.momentours.couplelog.command.application.service;

import beyond.momentours.couplelog.command.domain.aggregate.session.EditSessionInfo;
import beyond.momentours.member.command.application.dto.CustomUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static org.springframework.messaging.simp.SimpMessageHeaderAccessor.getSessionId;

@Service
@Slf4j
public class WebSocketServiceImpl implements WebSocketService {
    private final SimpMessagingTemplate messagingTemplate;
    private final RedisTemplate<String, EditSessionInfo> redisTemplate;
    // sessionId와 memberId를 매핑하기 위한 Map
    private final Map<String, Long> documentSubscribers = new ConcurrentHashMap<>();

    @Autowired
    public WebSocketServiceImpl(SimpMessagingTemplate messagingTemplate,
                                RedisTemplate<String, EditSessionInfo> redisTemplate) {
        this.messagingTemplate = messagingTemplate;
        this.redisTemplate = redisTemplate;
    }

    // 편집자 등록 - 문서 편집을 시작할 때 호출
    @Override
    public void registerEditor(Long documentId, CustomUserDetails user) {

    }
}
