package beyond.momentours.date_course_folder.command.application.controller;

import beyond.momentours.common.exception.CommonException;
import beyond.momentours.date_course_folder.command.application.dto.DateCourseFolderDTO;
import beyond.momentours.date_course_folder.command.application.mapper.DateCourseFolderConverter;
import beyond.momentours.date_course_folder.command.application.service.DateCourseFolderCommandService;
import beyond.momentours.date_course_folder.command.domain.vo.request.RequestCreateDateCourseFolderVO;
import beyond.momentours.date_course_folder.command.domain.vo.response.ResponseCreateDateCourseFolderVO;
import beyond.momentours.date_course_folder.command.domain.vo.response.ResponseUpdateDateCourseFolderVO;
import beyond.momentours.member.command.application.dto.CustomUserDetails;
//import beyond.momentours.util.FileUploader;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/course-folder")
@Slf4j
@RequiredArgsConstructor
public class DateCourseFolderCommandController {

    private final DateCourseFolderCommandService dateCourseFolderCommandService;
    private final DateCourseFolderConverter dateCourseFolderConverter;
//    private final FileUploader fileUploader;

    @Operation(description = "데이트 코스 폴더 생성")
    @PostMapping
    public ResponseEntity<?> createDateCourseFolder(@RequestBody @Valid RequestCreateDateCourseFolderVO request, @AuthenticationPrincipal CustomUserDetails user) {
        log.info("데이트 코스 폴더 생성 요청: {}", request);
        try {
            DateCourseFolderDTO folderDTO = dateCourseFolderConverter.fromCreateVOToDTO(request, user.getMemberId());
            DateCourseFolderDTO savedFolder = dateCourseFolderCommandService.createDateCourseFolder(folderDTO);
            ResponseCreateDateCourseFolderVO response = dateCourseFolderConverter.fromDTOToCreateVO(savedFolder);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (CommonException e) {
            log.error("데이트 코스 폴더 생성 오류: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            log.error("예상치 못한 오류", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("예상치 못한 오류가 발생했습니다.");
        }
    }

    @PatchMapping(value = "/{folderId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateDateCourseFolder(@PathVariable Long folderId, @RequestPart("folderName") String folderName, @RequestPart("folderDescription") String folderDescription, @RequestPart(value = "folder_image", required = false) MultipartFile image, @AuthenticationPrincipal CustomUserDetails user) {
        log.info("폴더 수정 요청: folderId={}, folderName={}, folderDescription={}", folderId, folderName, folderDescription);
        String imageUrl = null;
//    if (image != null && !image.isEmpty()) imageUrl = fileUploader.upload(image, "course-folder");

        DateCourseFolderDTO folderDTO = getDateCourseFolderDTO(folderId, folderName, folderDescription, imageUrl);

        DateCourseFolderDTO updatedFolder = dateCourseFolderCommandService.updateDateCourseFolder(folderDTO, user);
        ResponseUpdateDateCourseFolderVO response = dateCourseFolderConverter.fromDTOToUpdateVO(updatedFolder);
        return ResponseEntity.ok(response);
    }

    private DateCourseFolderDTO getDateCourseFolderDTO(Long folderId, String folderName, String folderDescription, String imageUrl) {
        DateCourseFolderDTO folderDTO = DateCourseFolderDTO.builder()
                .folderId(folderId)
                .folderName(folderName)
                .folderDescription(folderDescription)
                .folderImage(imageUrl)
                .build();
        return folderDTO;
    }

    @Operation(description = "데이트 코스 폴더 삭제")
    @DeleteMapping("/{folderId}")
    public ResponseEntity<?> deleteDateCourseFolder(@PathVariable Long folderId, @AuthenticationPrincipal CustomUserDetails user) {
        log.info("데이트 코스 폴더 삭제 요청: folderId={}, userId={}", folderId, user.getMemberId());
        try {
            dateCourseFolderCommandService.deleteDateCourseFolder(folderId, user);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (CommonException e) {
            log.error("데이트 코스 폴더 삭제 오류: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            log.error("예상치 못한 오류", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("예상치 못한 오류가 발생했습니다.");
        }
    }
}
