package beyond.momentours.like.scheduler;

import beyond.momentours.date_course.command.domain.repository.DateCourseRepository;
import beyond.momentours.moment.command.domain.aggregate.repository.MomentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Set;

@Component
@Slf4j
@RequiredArgsConstructor
public class LikeCountSyncScheduler {

    private final RedisTemplate<String, String> redisTemplate;
    private final MomentRepository momentRepository;
    private final DateCourseRepository dateCourseRepository;

    private static final String PREFIX = "like::count::";

    @Scheduled(fixedRate = 5 * 60 * 1000)
    public void syncLikeCounts() {
        log.info("좋아요 Redis DB 동기화 시작");

        Set<String> keys = redisTemplate.keys(PREFIX + "*");
        if (keys == null || keys.isEmpty()) return;

        for (String key : keys) {
            String[] parts = key.split("::");
            if (parts.length != 4) continue;

            String typeStr = parts[2];
            Long targetId = Long.parseLong(parts[3]);
            Long likeCount = Long.parseLong(redisTemplate.opsForValue().get(key));

            switch (typeStr) {
                case "MOMENT" -> {
                    momentRepository.updateLikeCount(targetId, likeCount);
                }
                case "DATE_COURSE" -> {
                    dateCourseRepository.updateLikeCount(targetId, likeCount);
                }
            }

            redisTemplate.expire(key, Duration.ofHours(24));
        }

        log.info("✅ 좋아요 동기화 완료");
    }
}
