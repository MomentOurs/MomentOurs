package beyond.momentours.like.query.service;

import beyond.momentours.like.command.domain.aggregate.LikeType;
import beyond.momentours.like.query.mapper.LikeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeQueryServiceImpl implements LikeQueryService {

    private final LikeMapper likeMapper;

    @Override
    public boolean hasUserLiked(Long memberId, LikeType type, Long targetId) {
        return likeMapper.existsByMemberAndTarget(memberId, type, targetId);
    }
}
