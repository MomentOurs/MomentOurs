package beyond.momentours.date_course_folder.query.controller;

import beyond.momentours.common.exception.CommonException;
import beyond.momentours.date_course_folder.command.application.dto.DateCourseFolderDTO;
import beyond.momentours.date_course_folder.command.application.mapper.DateCourseFolderConverter;
import beyond.momentours.date_course_folder.command.domain.vo.response.ResponseDateCourseFolderListVO;
import beyond.momentours.date_course_folder.query.service.DateCourseFolderQueryService;
import beyond.momentours.member.command.application.dto.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/course-folder")
@Slf4j
@RequiredArgsConstructor
public class DateCourseFolderQueryController {

    private final DateCourseFolderQueryService dateCourseFolderQueryService;
    private final DateCourseFolderConverter dateCourseFolderConverter;

    @Operation(description = "내가 만든 폴더 목록 조회")
    @GetMapping
    public ResponseEntity<?> getMyDateCourseFolders(@AuthenticationPrincipal CustomUserDetails user) {
        log.info("내 폴더 목록 조회 요청: userId={}", user.getMemberId());
        try {
            List<DateCourseFolderDTO> folders = dateCourseFolderQueryService.getMyDateCourseFolders(user);
            List<ResponseDateCourseFolderListVO> response = dateCourseFolderConverter.fromDTOToListVO(folders);
            return ResponseEntity.ok(response);
        } catch (CommonException e) {
            log.error("내 폴더 목록 조회 오류: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            log.error("예상치 못한 오류", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("예상치 못한 오류가 발생했습니다.");
        }
    }
}
