package beyond.momentours.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PlanMapper {

    Long findByMemberId(Long memberId);

    Long findByCoupleId(Long memberId);

    Long findByCourseId(Long courseId);
}
