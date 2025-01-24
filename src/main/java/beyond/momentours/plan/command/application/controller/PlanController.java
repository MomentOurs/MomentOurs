package beyond.momentours.plan.command.application.controller;

import beyond.momentours.common.exception.CommonException;
import beyond.momentours.member.command.application.dto.CustomUserDetails;
import beyond.momentours.plan.command.application.dto.PlanDTO;
import beyond.momentours.plan.command.application.mapper.PlanConverter;
import beyond.momentours.plan.command.application.service.PlanService;
import beyond.momentours.plan.command.domain.vo.request.RequestUpdatePlanVO;
import beyond.momentours.plan.command.domain.vo.request.RequestCreatePlanVO;
import beyond.momentours.plan.command.domain.vo.response.ResponseUpdatePlanVO;
import beyond.momentours.plan.command.domain.vo.response.ResponseCreatePlanVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("commandPlanController")
@RequestMapping("api/plan")
@Slf4j
@RequiredArgsConstructor
public class PlanController {

    private final PlanService planService;
    private final PlanConverter planConverter;

    @PostMapping
    public ResponseEntity<?> createPlan(@RequestBody RequestCreatePlanVO request, @AuthenticationPrincipal CustomUserDetails user) {
        log.info("등록 요청된 request 데이터 : {}", request);
        try {
            PlanDTO planDTO = planConverter.fromCreateVOToDTO(request);
            PlanDTO savePlanDTO = planService.createPlan(planDTO, user);
            ResponseCreatePlanVO response = planConverter.fromDTOToCreateVO(savePlanDTO);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (CommonException e) {
            log.error("일정 등록 오류: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            log.error("예상치 못한 오류", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("예상치 못한 오류가 발생했습니다");
        }
    }

    @PatchMapping
    public ResponseEntity<?> updatePlan(@RequestBody RequestUpdatePlanVO request, @AuthenticationPrincipal CustomUserDetails user) {
        log.info("수정 요청된 request 데이터 : {}", request);
        try {
            PlanDTO planDTO = planConverter.fromUpdateVOToDTO(request);
            PlanDTO updatedPlan = planService.updatePlan(planDTO, user);
            ResponseUpdatePlanVO response = planConverter.fromDTOToUpdateVO(updatedPlan);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (CommonException e) {
            log.error("일정 수정 오류: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            log.error("예상치 못한 오류", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("예상치 못한 오류가 발생했습니다");
        }
    }

    @PatchMapping("/deactivate/{planId}")
    public ResponseEntity<?> deletePlan(@PathVariable Long planId, @AuthenticationPrincipal CustomUserDetails user) {
        log.info("삭제 요청한 일정 ID : {}", planId);
        try {
            PlanDTO deletedPlan = planService.deletePlan(planId, user);
            log.info("삭제된 planId : {}, 해당 일정의 상태 : {}", deletedPlan.getPlanId(), deletedPlan.getPlanStatus());
            return ResponseEntity.status(HttpStatus.OK).body("성공적으로 삭제되었습니다.");
        } catch (CommonException e) {
            log.error("일정 삭제 오류: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            log.error("예상치 못한 오류", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("예상치 못한 오류가 발생했습니다");
        }
    }

    @GetMapping("/schedules")
    public ResponseEntity<?> getPlans(@RequestParam int year, @RequestParam int month, @AuthenticationPrincipal CustomUserDetails user) {
        log.info("스케줄 월별 조회 요청 year: {}, month: {}", year, month);
        try {
            List<PlanDTO> plans = planService.getPlans(year, month, user);
            return ResponseEntity.status(HttpStatus.OK).body(plans);
        } catch (CommonException e) {
            log.error("스케줄 월별 조회 오류: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            log.error("예상치 못한 오류", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("예상치 못한 오류가 발생했습니다");
        }
    }

    @GetMapping("/schedules/date")
    public ResponseEntity<?> getPlansByDate(@RequestParam int year, @RequestParam int month, @RequestParam int day, @AuthenticationPrincipal CustomUserDetails user) {
        log.info("특정 날짜 일정 요청 year: {}, month: {}, day: {}", year, month, day);
        try {
            List<PlanDTO> plansByDate = planService.getPlansByDate(year, month, day, user);
            return ResponseEntity.status(HttpStatus.OK).body(plansByDate);
        } catch (CommonException e) {
            log.error("스케줄 특정 날짜 조회 오류: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            log.error("예상치 못한 오류", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("예상치 못한 오류가 발생했습니다");
        }
    }

    @GetMapping("/{planId}")
    public ResponseEntity<?> getPlanById(@PathVariable Long planId) {
        log.info("조회 요청된 planId: {}", planId);
        try {
            PlanDTO plan = planService.getPlanById(planId);
            return ResponseEntity.status(HttpStatus.OK).body(plan);
        } catch (CommonException e) {
            log.error("스케줄 조회 오류: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            log.error("예상치 못한 오류", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("예상치 못한 오류가 발생했습니다");
        }
    }
}
