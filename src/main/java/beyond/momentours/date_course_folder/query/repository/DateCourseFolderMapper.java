package beyond.momentours.date_course_folder.query.repository;

import beyond.momentours.date_course_folder.command.application.dto.DateCourseFolderDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface DateCourseFolderMapper {
    List<DateCourseFolderDTO> findFoldersByMemberId(@Param("memberId") Long memberId);
}
