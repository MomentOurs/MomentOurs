package beyond.momentours.date_course.query.controller;

import beyond.momentours.common.exception.CommonException;
import beyond.momentours.date_course.command.application.dto.DateCourseDTO;
import beyond.momentours.date_course.command.application.mapper.DateCourseConverter;
import beyond.momentours.date_course.command.domain.vo.response.ResponseDateCourseDetailWithLocationVO;
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
            ResponseDateCourseDetailWithLocationVO response = dateCourseConverter.fromDTOToDetailVO(dateCourseDTO);
            return ResponseEntity.ok(response);
        } catch (CommonException e) {
            log.error("데이트 코스 조회 오류: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            log.error("예상치 못한 오류", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("예상치 못한 오류가 발생했습니다");
        }
    }

    @GetMapping("/folder")
    public ResponseEntity<?> getDateCoursesByFolder(@RequestParam(required = false) Long folderId, @AuthenticationPrincipal CustomUserDetails user) {
        log.info("폴더 내 데이트 코스 조회 요청: folderId={} (nullable)", folderId);
        try {
            List<DateCourseDTO> courses;
            if (folderId == null) {
                courses = dateCourseService.getCoursesWithoutFolder(user.getMemberId());
            } else {
                courses = dateCourseService.getCoursesByFolderId(folderId);
            }
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

    @GetMapping("/{courseScrapFolderId}/courses")
    public ResponseEntity<?> getCoursesInScrapFolder(@PathVariable Long courseScrapFolderId) {
        log.info("즐겨찾기 폴더 내 데이트 코스 조회 요청: scrapFolderId={}", courseScrapFolderId);
        try {
            List<DateCourseDTO> courses = dateCourseService.getCoursesByScrapFolderId(courseScrapFolderId);
            List<ResponseDateCourseListVO> response = dateCourseConverter.fromDTOToListVO(courses);
            return ResponseEntity.ok(response);
        } catch (CommonException e) {
            log.error("즐겨찾기 폴더 코스 조회 오류: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            log.error("예상치 못한 오류", e);
            return ResponseEntity.status(500).body("서버 오류 발생");
        }
    }
}
