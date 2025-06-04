package beyond.momentours.like.query.repository;

import beyond.momentours.like.command.domain.aggregate.LikeType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LikeMapper {

    boolean existsByMemberAndTarget(@Param("memberId") Long memberId, @Param("type") LikeType type, @Param("targetId") Long targetId);

}
