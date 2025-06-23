package beyond.momentours.like.scheduler;

import beyond.momentours.date_course.command.domain.repository.DateCourseRepository;
import beyond.momentours.moment.command.domain.aggregate.repository.MomentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
@RequiredArgsConstructor
public class LikeCountSyncScheduler {

    private final RedisTemplate<String, String> redisTemplate;
    private final MomentRepository momentRepository;
    private final DateCourseRepository dateCourseRepository;
    private final RedissonClient redissonClient;

    private static final String PREFIX = "like::count::";
    private static final String RETRY_PREFIX = "like::retry::";

    @Transactional
    @Scheduled(fixedRate = 5 * 60 * 1000)
    public void syncLikeCounts() {
        log.info("좋아요 Redis DB 동기화 시작");

        Set<String> keys = redisTemplate.keys(PREFIX + "*");
        if (keys == null || keys.isEmpty()) return;

        for (String key : keys) {
            RLock lock = redissonClient.getLock("lock::sync::" + key);
            try {
                if (!lock.tryLock(2, 1, TimeUnit.SECONDS)) continue;

                String[] parts = key.split("::");
                if (parts.length != 4) continue;

                String typeStr = parts[2];
                Long targetId = Long.parseLong(parts[3]);
                String value = redisTemplate.opsForValue().get(key);
                if (value == null) continue;

                Long likeCount = Long.parseLong(value);

                switch (typeStr) {
                    case "MOMENT" -> momentRepository.updateLikeCount(targetId, likeCount);
                    case "DATE_COURSE" -> dateCourseRepository.updateLikeCount(targetId, likeCount);
                    default -> throw new IllegalStateException("Unknown type: " + typeStr);
                }

                redisTemplate.delete(key);
            } catch (Exception e) {
                log.error("❌ 좋아요 동기화 실패 - key: {}", key, e);

                String retryKey = RETRY_PREFIX + key.substring(PREFIX.length());
                redisTemplate.opsForValue().set(retryKey, redisTemplate.opsForValue().get(key));
            } finally {
                if (lock.isHeldByCurrentThread()) lock.unlock();
            }
        }

        log.info("✅ 좋아요 동기화 완료");
    }

    @Transactional
    @Scheduled(fixedRate = 1 * 60 * 1000)
    public void retryFailedSyncs() {
        Set<String> retryKeys = redisTemplate.keys(RETRY_PREFIX + "*");
        if (retryKeys == null || retryKeys.isEmpty()) return;

        for (String retryKey : retryKeys) {
            String key = retryKey.replace(RETRY_PREFIX, PREFIX);
            RLock lock = redissonClient.getLock("lock::sync::retry::" + key);
            try {
                if (!lock.tryLock(2, 1, TimeUnit.SECONDS)) continue;

                String[] parts = key.split("::");
                if (parts.length != 4) continue;

                String typeStr = parts[2];
                Long targetId = Long.parseLong(parts[3]);
                String value = redisTemplate.opsForValue().get(retryKey);
                if (value == null) continue;

                Long likeCount = Long.parseLong(value);

                switch (typeStr) {
                    case "MOMENT" -> momentRepository.updateLikeCount(targetId, likeCount);
                    case "DATE_COURSE" -> dateCourseRepository.updateLikeCount(targetId, likeCount);
                    default -> throw new IllegalStateException("Unknown type: " + typeStr);
                }

                redisTemplate.delete(retryKey);
            } catch (Exception e) {
                log.error("❌ 재시도 동기화 실패 - retryKey: {}", retryKey, e);
            } finally {
                if (lock.isHeldByCurrentThread()) lock.unlock();
            }
        }
    }
}