package beyond.momentours.date_course.query.repository;

import beyond.momentours.date_course.command.domain.aggregate.entity.DateCourse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface DateCourseMapper {
    Optional<DateCourse> findActiveById(@Param("courseId") Long courseId);
    boolean existsActiveById(@Param("courseId") Long courseId);
    List<DateCourse> findCoursesWithSorting(@Param("sortBy") String sortBy);
    void incrementViewCountInDB(@Param("courseId") Long courseId, @Param("viewCount") Long viewCount);
    List<DateCourse> findCoursesByFolder_FolderId(@Param("folderId") Long folderId);
    List<DateCourse> findCoursesByScrapFolderId(@Param("courseScrapFolderId") Long courseScrapFolderId);

    List<DateCourse> findCoursesWithoutFolder(@Param("memberId")Long memberId);
}
