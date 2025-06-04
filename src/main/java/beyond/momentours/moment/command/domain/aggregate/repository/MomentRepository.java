package beyond.momentours.moment.command.domain.aggregate.repository;

import beyond.momentours.moment.command.domain.aggregate.entity.Moment;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MomentRepository extends JpaRepository<Moment, Long> {
    @Modifying
    @Query("UPDATE Moment m " +
            "  SET m.momentLike = :likeCount " +
            "WHERE m.momentId = :targetId")
    void updateLikeCount(@Param("targetId") Long targetId, @Param("likeCount") Long likeCount);

}
