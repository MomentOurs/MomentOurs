package beyond.momentours.plan.query.controller.controller;

import beyond.momentours.common.exception.CommonException;
import beyond.momentours.member.command.application.dto.CustomUserDetails;
import beyond.momentours.plan.command.application.dto.PlanDTO;
import beyond.momentours.plan.command.application.mapper.PlanConverter;
import beyond.momentours.plan.command.domain.vo.response.ResponseGetPlanVO;
import beyond.momentours.plan.query.service.PlanQueryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/plan")
@Slf4j
@RequiredArgsConstructor
public class PlanQueryController {

    private final PlanQueryService planQueryService;
    private final PlanConverter planConverter;

    @Operation(description = "스케줄 월별 조회 요청")
    @GetMapping("/schedules")
    public ResponseEntity<?> getPlans(@RequestParam int year, @RequestParam int month, @RequestParam(required = false) List<String> type, @AuthenticationPrincipal CustomUserDetails user) {
        log.info("스케줄 월별 조회 요청 year: {}, month: {}, type: {}", year, month, type);
        try {
            List<PlanDTO> plans = planQueryService.getPlans(year, month, type, user);
            List<ResponseGetPlanVO> response = plans.stream()
                    .map(planConverter::fromDTOToGetVO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(response);
        } catch (CommonException e) {
            log.error("스케줄 월별 조회 오류: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            log.error("예상치 못한 오류", e);
            return ResponseEntity.internalServerError().body("예상치 못한 오류가 발생했습니다");
        }
    }

    @Operation(description = "특정 날짜의 일정들 조회 요청")
    @GetMapping("/schedules/date")
    public ResponseEntity<?> getPlansByDate(@RequestParam int year, @RequestParam int month, @RequestParam int day, @AuthenticationPrincipal CustomUserDetails user) {
        log.info("특정 날짜 일정 요청 year: {}, month: {}, day: {}", year, month, day);
        try {
            List<PlanDTO> plansByDate = planQueryService.getPlansByDate(year, month, day, user);
            List<ResponseGetPlanVO> response = plansByDate.stream()
                    .map(planConverter::fromDTOToGetVO)
                    .collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (CommonException e) {
            log.error("스케줄 특정 날짜 조회 오류: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            log.error("예상치 못한 오류", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("예상치 못한 오류가 발생했습니다");
        }
    }

    @Operation(description = "특정 일정 조회")
    @GetMapping("/{planId}")
    public ResponseEntity<?> getPlanById(@PathVariable Long planId) {
        log.info("조회 요청된 planId: {}", planId);
        try {
            PlanDTO plan = planQueryService.getPlanById(planId);
            ResponseGetPlanVO response = planConverter.fromDTOToGetVO(plan);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (CommonException e) {
            log.error("스케줄 조회 오류: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            log.error("예상치 못한 오류", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("예상치 못한 오류가 발생했습니다");
        }
    }
}
