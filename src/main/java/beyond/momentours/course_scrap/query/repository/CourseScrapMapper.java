package beyond.momentours.course_scrap.query.repository;

import beyond.momentours.course_scrap.command.domain.vo.CourseScrapVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CourseScrapMapper {
    List<CourseScrapVO> findByFolderId(Long folderId);

    Long findFolderOwnerId(@Param("folderId") Long folderId);
}
