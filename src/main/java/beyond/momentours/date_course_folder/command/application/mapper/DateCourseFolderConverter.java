package beyond.momentours.date_course_folder.command.application.mapper;

import beyond.momentours.date_course_folder.command.application.dto.DateCourseFolderDTO;
import beyond.momentours.date_course_folder.command.domain.aggregate.entity.DateCourseFolder;
import beyond.momentours.date_course_folder.command.domain.vo.request.RequestCreateDateCourseFolderVO;
import beyond.momentours.date_course_folder.command.domain.vo.request.RequestUpdateDateCourseFolderVO;
import beyond.momentours.date_course_folder.command.domain.vo.response.ResponseCreateDateCourseFolderVO;
import beyond.momentours.date_course_folder.command.domain.vo.response.ResponseDateCourseFolderListVO;
import beyond.momentours.date_course_folder.command.domain.vo.response.ResponseUpdateDateCourseFolderVO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DateCourseFolderConverter {

    public DateCourseFolderDTO fromCreateVOToDTO(RequestCreateDateCourseFolderVO vo, Long memberId) {
        return DateCourseFolderDTO.builder()
                .folderName(vo.getFolderName())
                .memberId(memberId)
                .build();
    }

    public DateCourseFolderDTO fromUpdateVOToDTO(RequestUpdateDateCourseFolderVO vo, Long folderId) {
        return DateCourseFolderDTO.builder()
                .folderId(folderId)
                .folderName(vo.getFolderName())
                .build();
    }

    public ResponseCreateDateCourseFolderVO fromDTOToCreateVO(DateCourseFolderDTO dto) {
        return new ResponseCreateDateCourseFolderVO(dto.getFolderId(), dto.getFolderName());
    }

    public ResponseUpdateDateCourseFolderVO fromDTOToUpdateVO(DateCourseFolderDTO dto) {
        return new ResponseUpdateDateCourseFolderVO(dto.getFolderId(), dto.getFolderName());
    }

    public DateCourseFolderDTO fromEntityToDTO(DateCourseFolder entity) {
        return DateCourseFolderDTO.builder()
                .folderId(entity.getFolderId())
                .folderName(entity.getFolderName())
                .memberId(entity.getMemberId())
                .build();
    }

    public DateCourseFolder fromDTOToEntity(DateCourseFolderDTO dto) {
        return DateCourseFolder.builder()
                .folderId(dto.getFolderId())
                .folderName(dto.getFolderName())
                .memberId(dto.getMemberId())
                .build();
    }

    public List<ResponseDateCourseFolderListVO> fromDTOToListVO(List<DateCourseFolderDTO> dtos) {
        return dtos.stream()
                .map(dto -> new ResponseDateCourseFolderListVO(
                        dto.getFolderId(),
                        dto.getFolderName(),
                        dto.getFolderDescription(),
                        dto.getFolderImage(),
                        dto.getCourseCount()
                ))
                .collect(Collectors.toList());
    }
}
