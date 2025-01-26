package beyond.momentours.date_course.command.domain.repository;

import beyond.momentours.date_course.command.domain.aggregate.entity.DateCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DateCourseRepository extends JpaRepository<DateCourse, Long> {
}
