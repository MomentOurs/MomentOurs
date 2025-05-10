package beyond.momentours.course_scrap.command.application.mapper;

import beyond.momentours.course_scrap.command.application.dto.CourseScrapDTO;
import beyond.momentours.course_scrap.command.domain.aggregate.entity.CourseScrap;
import beyond.momentours.course_scrap.command.domain.vo.request.RequestCreateCourseScrapVO;
import beyond.momentours.course_scrap.command.domain.vo.response.ResponseCreateCourseScrapVO;
import org.springframework.stereotype.Component;

@Component
public class CourseScrapConverter {

    public CourseScrapDTO fromCreateVOToDTO(RequestCreateCourseScrapVO vo) {
        return CourseScrapDTO.builder()
                .courseScrapFolderId(vo.getCourseScrapFolderId())
                .courseId(vo.getCourseId())
                .build();
    }

    public ResponseCreateCourseScrapVO fromDTOToCreateVO(CourseScrapDTO dto) {
        return ResponseCreateCourseScrapVO.builder()
                .courseScrapId(dto.getCourseScrapId())
                .courseScrapFolderId(dto.getCourseScrapFolderId())
                .courseId(dto.getCourseId())
                .createdAt(dto.getCreatedAt())
                .build();
    }

    public CourseScrapDTO fromEntityToDTO(CourseScrap entity) {
        return CourseScrapDTO.builder()
                .courseScrapId(entity.getCourseScrapId())
                .courseScrapFolderId(entity.getCourseScrapFolderId())
                .courseId(entity.getCourseId())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    public CourseScrap fromDTOToEntity(CourseScrapDTO scrapDTO) {
        return CourseScrap.builder()
                .courseScrapId(scrapDTO.getCourseScrapId())
                .courseScrapFolderId(scrapDTO.getCourseScrapFolderId())
                .courseId(scrapDTO.getCourseId())
                .build();
    }
}
