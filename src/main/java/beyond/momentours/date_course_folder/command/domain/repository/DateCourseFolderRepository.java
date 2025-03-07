package beyond.momentours.date_course_folder.command.domain.repository;

import beyond.momentours.date_course_folder.command.domain.aggregate.entity.DateCourseFolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DateCourseFolderRepository extends JpaRepository<DateCourseFolder, Long> {
}
