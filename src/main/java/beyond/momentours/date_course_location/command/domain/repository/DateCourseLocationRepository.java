package beyond.momentours.date_course_location.command.domain.repository;

import beyond.momentours.date_course_location.command.domain.aggregate.entity.DateCourseLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DateCourseLocationRepository extends JpaRepository<DateCourseLocation, Long> {
    void deleteByCourseId(Long courseId);
}
