package beyond.momentours.date_course_folder.command.application.controller;

import beyond.momentours.common.exception.CommonException;
import beyond.momentours.date_course_folder.command.application.dto.DateCourseFolderDTO;
import beyond.momentours.date_course_folder.command.application.mapper.DateCourseFolderConverter;
import beyond.momentours.date_course_folder.command.application.service.DateCourseFolderCommandService;
import beyond.momentours.date_course_folder.command.domain.vo.request.RequestCreateDateCourseFolderVO;
import beyond.momentours.date_course_folder.command.domain.vo.request.RequestUpdateDateCourseFolderVO;
import beyond.momentours.date_course_folder.command.domain.vo.response.ResponseCreateDateCourseFolderVO;
import beyond.momentours.date_course_folder.command.domain.vo.response.ResponseUpdateDateCourseFolderVO;
import beyond.momentours.member.command.application.dto.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/course-folder")
@Slf4j
@RequiredArgsConstructor
public class DateCourseFolderCommandController {

    private final DateCourseFolderCommandService dateCourseFolderCommandService;
    private final DateCourseFolderConverter dateCourseFolderConverter;

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

    @Operation(description = "데이트 코스 폴더 이름 변경")
    @PatchMapping("/{folderId}")
    public ResponseEntity<?> updateDateCourseFolder(
            @PathVariable Long folderId,
            @RequestBody @Valid RequestUpdateDateCourseFolderVO request,
            @AuthenticationPrincipal CustomUserDetails user) {
        log.info("데이트 코스 폴더 이름 변경 요청: folderId={}, request={}", folderId, request);
        try {
            DateCourseFolderDTO folderDTO = dateCourseFolderConverter.fromUpdateVOToDTO(request, folderId);
            DateCourseFolderDTO updatedFolder = dateCourseFolderCommandService.updateDateCourseFolder(folderDTO, user);
            ResponseUpdateDateCourseFolderVO response = dateCourseFolderConverter.fromDTOToUpdateVO(updatedFolder);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (CommonException e) {
            log.error("데이트 코스 폴더 수정 오류: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            log.error("예상치 못한 오류", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("예상치 못한 오류가 발생했습니다.");
        }
    }

    @Operation(description = "데이트 코스 폴더 삭제 (Soft Delete)")
    @DeleteMapping("/{folderId}")
    public ResponseEntity<?> deleteDateCourseFolder(
            @PathVariable Long folderId,
            @AuthenticationPrincipal CustomUserDetails user) {
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
