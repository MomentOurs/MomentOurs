package beyond.momentours.date_course.command.application.controller;

import beyond.momentours.common.exception.CommonException;
import beyond.momentours.date_course.command.application.dto.DateCourseDTO;
import beyond.momentours.date_course.command.application.mapper.DateCourseConverter;
import beyond.momentours.date_course.command.application.service.DateCourseService;
import beyond.momentours.date_course.command.domain.vo.request.RequestCreateDateCourseVO;
import beyond.momentours.date_course.command.domain.vo.response.ResponseCreateDateCourseVO;
import beyond.momentours.member.command.application.dto.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
