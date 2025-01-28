package beyond.momentours.course_scrap_folder.command.application.controller;

import beyond.momentours.common.exception.CommonException;
import beyond.momentours.course_scrap_folder.command.application.dto.CourseScrapFolderDTO;
import beyond.momentours.course_scrap_folder.command.application.mapper.CourseScrapFolderConverter;
import beyond.momentours.course_scrap_folder.command.application.service.CourseScrapFolderService;
import beyond.momentours.course_scrap_folder.command.domain.vo.request.RequestCreateCourseScrapFolderVO;
import beyond.momentours.course_scrap_folder.command.domain.vo.response.ResponseCourseScrapFolderVO;
import beyond.momentours.course_scrap_folder.command.domain.vo.response.ResponseCreateCourseScrapFolderVO;
import beyond.momentours.member.command.application.dto.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("commandDateCourseScrapFolderController")
@RequestMapping("api/course-scrap-folder")
@Slf4j
@RequiredArgsConstructor
public class CourseScrapFolderController {

    private final CourseScrapFolderService courseScrapFolderService;
    private final CourseScrapFolderConverter courseScrapFolderConverter;

    @PostMapping
    public ResponseEntity<?> createCourseScrapFolder(@RequestBody RequestCreateCourseScrapFolderVO request, @AuthenticationPrincipal CustomUserDetails user) {
        log.info("데이트 코스 즐겨찾기 폴더 생성 요청: {}", request);
        try {
            CourseScrapFolderDTO folderDTO = courseScrapFolderConverter.fromCreateVOToDTO(request);
            CourseScrapFolderDTO savedFolderDTO = courseScrapFolderService.createFolder(folderDTO, user);
            ResponseCreateCourseScrapFolderVO response = courseScrapFolderConverter.fromDTOToCreateVO(savedFolderDTO);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            log.error("폴더 생성 실패", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("폴더 생성 중 오류가 발생했습니다.");
        }
    }

    @DeleteMapping("/{folderId}")
    public ResponseEntity<?> deleteCourseScrapFolder(@PathVariable Long folderId, @AuthenticationPrincipal CustomUserDetails user) {
        log.info("폴더 삭제 요청: folderId={}, user={}", folderId, user.getMemberId());
        try {
            courseScrapFolderService.deleteFolder(folderId, user);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (CommonException e) {
            log.error("폴더 삭제 오류: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            log.error("폴더 삭제 중 예상치 못한 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("폴더 삭제 중 오류가 발생했습니다.");
        }
    }

    @GetMapping
    public ResponseEntity<?> getCourseScrapFolders(@AuthenticationPrincipal CustomUserDetails user) {
        log.info("사용자가 등록한 즐겨찾기 폴더 조회 요청: userId={}", user.getMemberId());
        try {
            List<ResponseCourseScrapFolderVO> folders = courseScrapFolderService.getFoldersByMemberId(user.getMemberId());
            return ResponseEntity.status(HttpStatus.OK).body(folders);
        } catch (Exception e) {
            log.error("즐겨찾기 폴더 조회 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("즐겨찾기 폴더 조회 중 오류가 발생했습니다.");
        }
    }
}
