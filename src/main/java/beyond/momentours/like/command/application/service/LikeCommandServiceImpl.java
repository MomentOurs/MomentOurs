package beyond.momentours.like.command.application.service;

import beyond.momentours.like.command.domain.aggregate.LikeType;
import beyond.momentours.like.command.domain.aggregate.entity.Like;
import beyond.momentours.like.command.domain.repository.LikeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class LikeCommandServiceImpl implements LikeCommandService {

    private final LikeRepository likeRepository;
    private final RedisTemplate<String, String> redisTemplate;
    private static final String REDIS_LIKE_COUNT_PREFIX = "like::count::";
    private final RedissonClient redissonClient;

    @Transactional
    @Override
    public void like(Long memberId, LikeType type, Long targetId) {
        String lockKey = "lock::like::" + type + "::" + targetId + "::" + memberId;
        RLock lock = redissonClient.getLock(lockKey);

        try {
            if (lock.tryLock(2, 1, TimeUnit.SECONDS)) {
                if (likeRepository.existsByMemberIdAndLikeTypeAndTargetId(memberId, type, targetId)) {
                    throw new IllegalStateException("이미 좋아요를 눌렀습니다.");
                }

                likeRepository.save(Like.builder()
                        .memberId(memberId)
                        .likeType(type)
                        .targetId(targetId)
                        .build()
                );

                String key = REDIS_LIKE_COUNT_PREFIX + type + "::" + targetId;
                redisTemplate.opsForValue().increment(key);
            } else {
                throw new RuntimeException("잠시 후 다시 시도해주세요.");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("락 획득 실패", e);
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

    @Transactional
    @Override
    public void unlike(Long memberId, LikeType type, Long targetId) {
        String lockKey = "lock::like::" + type + "::" + targetId + "::" + memberId;
        RLock lock = redissonClient.getLock(lockKey);

        try {
            if (lock.tryLock(2, 1, TimeUnit.SECONDS)) {
                likeRepository.deleteByMemberIdAndLikeTypeAndTargetId(memberId, type, targetId);
                redisTemplate.opsForValue().decrement(REDIS_LIKE_COUNT_PREFIX + type + "::" + targetId);
            } else {
                throw new RuntimeException("잠시 후 다시 시도해주세요.");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("락 획득 실패", e);
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

    @Override
    public Long getLikeCount(LikeType type, Long targetId) {
        String key = REDIS_LIKE_COUNT_PREFIX + type + "::" + targetId;
        String cached = redisTemplate.opsForValue().get(key);

        if (cached != null) return Long.parseLong(cached);

        Long count = likeRepository.countByLikeTypeAndTargetId(type, targetId);
        redisTemplate.opsForValue().set(key, String.valueOf(count));
        return count;
    }
}
