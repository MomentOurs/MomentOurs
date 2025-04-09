package beyond.momentours.course_scrap_folder.command.application.mapper;

import beyond.momentours.course_scrap_folder.command.application.dto.CourseScrapFolderDTO;
import beyond.momentours.course_scrap_folder.command.domain.aggregate.entity.CourseScrapFolder;
import beyond.momentours.course_scrap_folder.command.domain.vo.request.RequestCreateCourseScrapFolderVO;
import beyond.momentours.course_scrap_folder.command.domain.vo.response.ResponseCourseScrapFolderVO;
import beyond.momentours.course_scrap_folder.command.domain.vo.response.ResponseCreateCourseScrapFolderVO;
import org.springframework.stereotype.Component;

@Component
public class CourseScrapFolderConverter {
    public CourseScrapFolderDTO fromCreateVOToDTO(RequestCreateCourseScrapFolderVO request) {
        return CourseScrapFolderDTO.builder()
                .folderName(request.getFolderName())
                .folderDescription(request.getFolderDescription())
//                .folderImage( // S3나 저장 로직 후 URL 저장 )
                .build();
    }

    public ResponseCreateCourseScrapFolderVO fromDTOToCreateVO(CourseScrapFolderDTO folderDTO) {
        return ResponseCreateCourseScrapFolderVO.builder()
                .courseScrapFolderId(folderDTO.getCourseScrapFolderId())
                .folderName(folderDTO.getFolderName())
                .folderDescription(folderDTO.getFolderDescription())
//                .folderImage(folderDTO.getFolderImage())
                .memberId(folderDTO.getMemberId())
                .createdAt(folderDTO.getCreatedAt())
                .updatedAt(folderDTO.getUpdatedAt())
                .build();
    }

    public CourseScrapFolder fromDTOToEntity(CourseScrapFolderDTO folderDTO) {
        return CourseScrapFolder.builder()
                .folderName(folderDTO.getFolderName())
                .folderDescription(folderDTO.getFolderDescription())
//                .folderImage(folderDTO.getFolderImage())
                .memberId(folderDTO.getMemberId())
                .build();
    }

    public CourseScrapFolderDTO fromEntityToDTO(CourseScrapFolder folder) {
        return CourseScrapFolderDTO.builder()
                .courseScrapFolderId(folder.getCourseScrapFolderId())
                .folderName(folder.getFolderName())
                .folderDescription(folder.getFolderDescription())
                .folderImage(folder.getFolderImage())
                .memberId(folder.getMemberId())
                .createdAt(folder.getCreatedAt())
                .updatedAt(folder.getUpdatedAt())
                .build();
    }

    public ResponseCourseScrapFolderVO fromEntityToResponseVO(CourseScrapFolder folder, int courseCount) {
        return ResponseCourseScrapFolderVO.builder()
                .courseScrapFolderId(folder.getCourseScrapFolderId())
                .folderName(folder.getFolderName())
                .folderDescription(folder.getFolderDescription())
                .folderImage(folder.getFolderImage())
                .createdAt(folder.getCreatedAt())
                .updatedAt(folder.getUpdatedAt())
                .courseCount(courseCount)
                .build();
    }

}
