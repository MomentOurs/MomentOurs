package beyond.momentours.couplelog.command.application.service;

import beyond.momentours.couplelog.command.domain.aggregate.session.EditSessionInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class WebSocketServiceImpl implements WebSocketService {
    private final SimpMessagingTemplate messagingTemplate;
    private final RedisTemplate<String, EditSessionInfo> redisTemplate;
    private final Map<String, Set<String>> documentSubscribers = new ConcurrentHashMap<>();

    @Autowired
    public WebSocketServiceImpl(SimpMessagingTemplate messagingTemplate,
                                RedisTemplate<String, EditSessionInfo> redisTemplate) {
        this.messagingTemplate = messagingTemplate;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void registerEditor(Long couplelogId, Long memberId) {

    }
}
