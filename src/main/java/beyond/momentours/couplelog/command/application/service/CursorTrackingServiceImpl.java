package beyond.momentours.couplelog.command.application.service;

import beyond.momentours.couplelog.command.domain.aggregate.session.EditSessionInfo;
import beyond.momentours.member.command.application.dto.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class CursorTrackingServiceImpl implements CursorTrackingService {
    private final RedisTemplate<String, EditSessionInfo> redisTemplate;

    @Autowired
    public CursorTrackingServiceImpl(RedisTemplate<String, EditSessionInfo> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void startUpdating(Long couplelogId, CustomUserDetails user) {
        String key = "editing:session:" + couplelogId;
        Long memberId = user.getMember().getMemberId();
        EditSessionInfo sessionInfo = new EditSessionInfo(memberId);

        // 편집 세션 정보 저장
        redisTemplate.opsForValue().set(key, sessionInfo, 10, TimeUnit.MINUTES);
    }
}
