package beyond.momentours.plan.query.repository;

import beyond.momentours.plan.command.domain.aggregate.entity.Plan;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface PlanMapper {

    Long findByCoupleId(Long memberId);

    Long findByCourseId(Long courseId);

    List<Plan> findByCoupleIdAndDateRange(Long coupleId, LocalDateTime planStartDate, LocalDateTime planEndDate);
}
