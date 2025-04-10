package beyond.momentours.date_course.command.domain.repository;

import beyond.momentours.date_course.command.domain.aggregate.entity.DateCourse;
import jakarta.transaction.Transactional;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DateCourseRepository extends JpaRepository<DateCourse, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE DateCourse dc " +
            "SET dc.folderId = null " +
            "WHERE dc.folderId = :folderId")
    void clearFolderByFolderId(@Param("folderId") Long folderId);
}
