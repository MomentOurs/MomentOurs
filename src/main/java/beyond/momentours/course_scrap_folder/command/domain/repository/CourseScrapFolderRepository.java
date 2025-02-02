package beyond.momentours.course_scrap_folder.command.domain.repository;

import beyond.momentours.course_scrap_folder.command.domain.aggregate.entity.CourseScrapFolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseScrapFolderRepository extends JpaRepository<CourseScrapFolder, Long> {
}
