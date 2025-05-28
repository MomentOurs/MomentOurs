package beyond.momentours.like.query.service;

import beyond.momentours.like.command.domain.aggregate.LikeType;

public interface LikeQueryService {
    boolean hasUserLiked(Long memberId, LikeType type, Long targetId);
}
