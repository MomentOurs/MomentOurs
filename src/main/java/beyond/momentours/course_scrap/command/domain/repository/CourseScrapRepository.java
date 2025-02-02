package beyond.momentours.course_scrap.command.domain.repository;

import beyond.momentours.course_scrap.command.domain.aggregate.entity.CourseScrap;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseScrapRepository extends JpaRepository<CourseScrap, Long> {
}
