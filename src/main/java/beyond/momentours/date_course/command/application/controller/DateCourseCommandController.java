package beyond.momentours.date_course.command.application.controller;

import beyond.momentours.common.exception.CommonException;
import beyond.momentours.date_course.command.application.dto.DateCourseDTO;
import beyond.momentours.date_course.command.application.mapper.DateCourseConverter;
import beyond.momentours.date_course.command.application.service.DateCourseCommandService;
import beyond.momentours.date_course.command.domain.vo.request.RequestCreateDateCourseVO;
import beyond.momentours.date_course.command.domain.vo.request.RequestUpdateDateCourseScheduleVO;
import beyond.momentours.date_course.command.domain.vo.request.RequestUpdateDateCourseVO;
import beyond.momentours.date_course.command.domain.vo.response.ResponseCreateDateCourseVO;
import beyond.momentours.date_course.command.domain.vo.response.ResponseUpdateDateCourseVO;
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
@RequestMapping("api/course")
@Slf4j
@RequiredArgsConstructor
public class DateCourseCommandController {

    private final DateCourseCommandService dateCourseCommandService;
    private final DateCourseConverter dateCourseConverter;

    @Operation(description = "데이트 코스 등록 시")
    @PostMapping
    public ResponseEntity<?> createDateCourse(@RequestBody RequestCreateDateCourseVO request, @AuthenticationPrincipal CustomUserDetails user) {
        log.info("등록 요청된 데이트 코스 데이터 : {}", request);
        try {
            DateCourseDTO dateCourseDTO = dateCourseConverter.fromCreateVOToDTO(request);
            DateCourseDTO saveDateCourseDTO = dateCourseCommandService.createDateCourse(dateCourseDTO, user);
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

    @Operation(description = "데이트 코스 수정 시 (폴더 이동 X, 코스의 정보 수정 O)")
    @PatchMapping("/{courseId}")
    public ResponseEntity<?> updateDateCourse(@PathVariable Long courseId, @RequestBody RequestUpdateDateCourseVO request, @AuthenticationPrincipal CustomUserDetails user) {
        log.info("수정 요청된 데이트 코스 데이터 : {}", request);
        try {
            DateCourseDTO dateCourseDTO = dateCourseConverter.fromUpdateVOToDTO(request, courseId);
            DateCourseDTO updatedCourseDTO = dateCourseCommandService.updateDateCourse(dateCourseDTO, user);
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

    @Operation(description = "데이트 코스 삭제 시 (soft delete)")
    @PatchMapping("/deactivate/{courseId}")
    public ResponseEntity<?> deleteDateCourse(@PathVariable Long courseId, @AuthenticationPrincipal CustomUserDetails user) {
        log.info("데이트 코스 Soft Delete 요청: courseId={}, userId={}", courseId, user.getMemberId());
        try {
            dateCourseCommandService.deleteDateCourse(courseId, user);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (CommonException e) {
            log.error("데이트 코스 Soft Delete 오류: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            log.error("예상치 못한 오류", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("예상치 못한 오류가 발생했습니다");
        }
    }

    @Operation(description = "본인이 작성한 데이트 코스 증명 시 certification 변경")
    @PatchMapping("/certification/{courseId}")
    public ResponseEntity<?> certificationCourse(@PathVariable Long courseId, @AuthenticationPrincipal CustomUserDetails user) {
        log.info("데이트 코스 인증 요청: courseId={}, userId={}", courseId, user.getMemberId());
        try {
            dateCourseCommandService.certifyDateCourse(courseId, user);
            return ResponseEntity.status(HttpStatus.OK).body("데이트 코스가 인증되었습니다.");
        } catch (CommonException e) {
            log.error("데이트 코스 인증 오류: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            log.error("예상치 못한 오류", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("예상치 못한 오류가 발생했습니다");
        }
    }

    @Operation(description = "데이트 코스에서 특정 코스 일정 등록할 때 null이었던 시작일, 종료일 수정")
    @PatchMapping("/{courseId}/schedule")
    public ResponseEntity<?> updateCourseSchedule(@PathVariable Long courseId, @RequestBody @Valid RequestUpdateDateCourseScheduleVO request, @AuthenticationPrincipal CustomUserDetails user) {
        log.info("데이트 코스 일정 업데이트 요청: courseId={}, userId={}, start={}, end={}",
                courseId, user.getMemberId(), request.getCourseStartDate(), request.getCourseEndDate());
        try {
            DateCourseDTO dateCourseDTO = dateCourseConverter.fromUpdateScheduleVOToDTO(request, courseId);
            DateCourseDTO updatedCourseDTO = dateCourseCommandService.updateCourseSchedule(dateCourseDTO, user);
            ResponseUpdateDateCourseVO response = dateCourseConverter.fromDTOToUpdateVO(updatedCourseDTO);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (CommonException e) {
            log.error("데이트 코스 일정 업데이트 오류: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            log.error("예상치 못한 오류", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("예상치 못한 오류가 발생했습니다");
        }
    }

}
