package beyond.momentours.date_course_location.query.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DateCourseLocationMapper {
    List<Long> findLocationIdsByCourseId(@Param("courseId") Long courseId);

    int countMomentsByLocationIds(@Param("list") List<Long> locationIds);
}
