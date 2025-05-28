package beyond.momentours.like.command.domain.repository;

import beyond.momentours.like.command.domain.aggregate.LikeType;
import beyond.momentours.like.command.domain.aggregate.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    boolean existsByMemberIdAndLikeTypeAndTargetId(Long memberId, LikeType likeType, Long targetId);
    void deleteByMemberIdAndLikeTypeAndTargetId(Long memberId, LikeType likeType, Long targetId);
    Long countByLikeTypeAndTargetId(LikeType likeType, Long targetId);
}
