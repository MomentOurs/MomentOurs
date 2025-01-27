package beyond.momentours.date_course.query.repository;

import beyond.momentours.date_course.command.domain.aggregate.entity.DateCourse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

@Mapper
public interface DateCourseMapper {
    Optional<DateCourse> findActiveById(@Param("courseId") Long courseId);
}
