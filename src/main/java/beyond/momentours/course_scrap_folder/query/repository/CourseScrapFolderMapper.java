package beyond.momentours.course_scrap_folder.query.repository;

import beyond.momentours.course_scrap_folder.command.domain.aggregate.entity.CourseScrapFolder;
import beyond.momentours.course_scrap_folder.command.domain.vo.CourseScrapCountVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CourseScrapFolderMapper {
    List<CourseScrapFolder> findByMemberId(@Param("memberId") Long memberId);

    List<CourseScrapCountVO> countCoursesByFolderIds(@Param("folderIds") List<Long> folderIds);

}
