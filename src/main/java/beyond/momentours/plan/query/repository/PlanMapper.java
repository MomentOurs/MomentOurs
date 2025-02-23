package beyond.momentours.plan.query.repository;

import beyond.momentours.plan.command.domain.aggregate.entity.Plan;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface PlanMapper {

    Long findByCoupleId(Long memberId);

    Long findByCourseId(Long courseId);

    List<Plan> findByTypeAndDateRange(@Param("coupleId") Long coupleId, @Param("planStartDate") LocalDateTime planStartDate, @Param("planEndDate") LocalDateTime planEndDate, @Param("types") List<String> types);

    List<Plan> findByMemberOrCoupleIdAndDateRange(@Param("memberId") Long memberId, @Param("coupleId") Long coupleId, @Param("planStartDate") LocalDateTime planStartDate, @Param("planEndDate") LocalDateTime planEndDate);
}
