package beyond.momentours.date_course.query.controller;

import beyond.momentours.common.exception.CommonException;
import beyond.momentours.date_course.command.application.dto.DateCourseDTO;
import beyond.momentours.date_course.command.application.mapper.DateCourseConverter;
import beyond.momentours.date_course.command.domain.vo.response.ResponseDateCourseDetailVO;
import beyond.momentours.date_course.command.domain.vo.response.ResponseDateCourseListVO;
import beyond.momentours.date_course.query.service.DateCourseQueryService;
import beyond.momentours.member.command.application.dto.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/course")
@Slf4j
@RequiredArgsConstructor
public class DateCourseQueryController {

    private final DateCourseQueryService dateCourseService;
    private final DateCourseConverter dateCourseConverter;

    @GetMapping
    public ResponseEntity<?> getDateCourses(@RequestParam(value = "sortBy", defaultValue = "likes") String sortBy) {
        log.info("데이트 코스 목록 조회 요청: sortBy={}", sortBy);
        try {
            List<DateCourseDTO> courses = dateCourseService.getDateCourses(sortBy);
            List<ResponseDateCourseListVO> response = dateCourseConverter.fromDTOToListVO(courses);
            return ResponseEntity.ok(response);
        } catch (CommonException e) {
            log.error("데이트 코스 목록 조회 오류: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            log.error("예상치 못한 오류", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("예상치 못한 오류가 발생했습니다");
        }
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<?> getDateCourse(@PathVariable Long courseId, @AuthenticationPrincipal CustomUserDetails user) {
        log.info("데이트 코스 상세 조회 요청: courseId={}, userId={}", courseId, user.getMemberId());
        try {
            DateCourseDTO dateCourseDTO = dateCourseService.getDateCourse(courseId, user);
            return ResponseEntity.ok(dateCourseDTO);
        } catch (CommonException e) {
            log.error("데이트 코스 조회 오류: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            log.error("예상치 못한 오류", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("예상치 못한 오류가 발생했습니다");
        }
    }

    @GetMapping("/folder/{folderId}")
    public ResponseEntity<?> getDateCoursesByFolder(@PathVariable Long folderId) {
        log.info("폴더 내 데이트 코스 조회 요청: folderId={}", folderId);
        try {
            List<DateCourseDTO> courses = dateCourseService.getCoursesByFolderId(folderId);
            List<ResponseDateCourseListVO> response = dateCourseConverter.fromDTOToListVO(courses);
            return ResponseEntity.ok(response);
        } catch (CommonException e) {
            log.error("폴더 내 데이트 코스 조회 오류: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            log.error("예상치 못한 오류", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("예상치 못한 오류가 발생했습니다");
        }
    }

}
