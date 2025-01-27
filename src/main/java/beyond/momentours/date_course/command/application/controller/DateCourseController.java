package beyond.momentours.date_course.command.application.controller;

import beyond.momentours.common.exception.CommonException;
import beyond.momentours.date_course.command.application.dto.DateCourseDTO;
import beyond.momentours.date_course.command.application.mapper.DateCourseConverter;
import beyond.momentours.date_course.command.application.service.DateCourseService;
import beyond.momentours.date_course.command.domain.vo.request.RequestCreateDateCourseVO;
import beyond.momentours.date_course.command.domain.vo.request.RequestUpdateDateCourseVO;
import beyond.momentours.date_course.command.domain.vo.response.ResponseCreateDateCourseVO;
import beyond.momentours.date_course.command.domain.vo.response.ResponseDateCourseDetailVO;
import beyond.momentours.date_course.command.domain.vo.response.ResponseDateCourseListVO;
import beyond.momentours.date_course.command.domain.vo.response.ResponseUpdateDateCourseVO;
import beyond.momentours.member.command.application.dto.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("commandDateCourseController")
@RequestMapping("api/course")
@Slf4j
@RequiredArgsConstructor
public class DateCourseController {

    private final DateCourseService dateCourseService;
    private final DateCourseConverter dateCourseConverter;

    @PostMapping
    public ResponseEntity<?> createDateCourse(@RequestBody RequestCreateDateCourseVO request, @AuthenticationPrincipal CustomUserDetails user) {
        log.info("등록 요청된 데이트 코스 데이터 : {}", request);
        try {
            DateCourseDTO dateCourseDTO = dateCourseConverter.fromCreateVOToDTO(request);
            DateCourseDTO saveDateCourseDTO = dateCourseService.createDateCourse(dateCourseDTO, user);
            ResponseCreateDateCourseVO response = dateCourseConverter.fromDTOToCreateVO(saveDateCourseDTO);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (CommonException e) {
            log.error("데이트 코스 등록 오류: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            log.error("예상치 못한 오류", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("예상치 못한 오류가 발생했습니다");
        }
    }

    @PatchMapping("/{courseId}")
    public ResponseEntity<?> updateDateCourse(@PathVariable Long courseId, @RequestBody RequestUpdateDateCourseVO request, @AuthenticationPrincipal CustomUserDetails user) {
        log.info("수정 요청된 데이트 코스 데이터 : {}", request);
        try {
            DateCourseDTO dateCourseDTO = dateCourseConverter.fromUpdateVOToDTO(request, courseId);
            DateCourseDTO updatedCourseDTO = dateCourseService.updateDateCourse(dateCourseDTO, user);
            ResponseUpdateDateCourseVO response = dateCourseConverter.fromDTOToUpdateVO(updatedCourseDTO);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (CommonException e) {
            log.error("데이트 코스 수정 오류: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            log.error("예상치 못한 오류", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("예상치 못한 오류가 발생했습니다");
        }
    }

    @PatchMapping("/deactivate/{courseId}")
    public ResponseEntity<?> deleteDateCourse(@PathVariable Long courseId, @AuthenticationPrincipal CustomUserDetails user) {
        log.info("데이트 코스 Soft Delete 요청: courseId={}, userId={}", courseId, user.getMemberId());
        try {
            dateCourseService.deleteDateCourse(courseId, user);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (CommonException e) {
            log.error("데이트 코스 Soft Delete 오류: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            log.error("예상치 못한 오류", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("예상치 못한 오류가 발생했습니다");
        }
    }

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
            ResponseDateCourseDetailVO response = dateCourseConverter.fromDTOToDetailVO(dateCourseDTO);
            return ResponseEntity.ok(response);
        } catch (CommonException e) {
            log.error("데이트 코스 조회 오류: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            log.error("예상치 못한 오류", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("예상치 못한 오류가 발생했습니다");
        }
    }
}
