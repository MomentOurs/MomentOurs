package beyond.momentours.report.query.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ReportMapper {
    Long findMomentOwner(@Param("momentId") Long momentId);
    Long findDateCourseOwner(@Param("courseId") Long courseId);
}

