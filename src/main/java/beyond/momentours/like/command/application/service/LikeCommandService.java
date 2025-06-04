package beyond.momentours.like.command.application.service;

import beyond.momentours.like.command.domain.aggregate.LikeType;
import jakarta.transaction.Transactional;

public interface LikeCommandService {
    @Transactional
    void like(Long memberId, LikeType type, Long targetId);

    @Transactional
    void unlike(Long memberId, LikeType type, Long targetId);

    Long getLikeCount(LikeType type, Long targetId);
}
