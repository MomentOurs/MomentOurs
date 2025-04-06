package beyond.momentours.date_course_location.query.repository;

import beyond.momentours.date_course_location.command.domain.aggregate.entity.DateCourseLocation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DateCourseLocationMapper {
    List<DateCourseLocation> findByCourseId(@Param("courseId") Long courseId);

    List<Long> findLocationIdsByCourseId(@Param("courseId") Long courseId);

    int countMomentsByLocationIds(@Param("list") List<Long> locationIds);
}
