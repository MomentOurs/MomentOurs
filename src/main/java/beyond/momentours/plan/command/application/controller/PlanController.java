package beyond.momentours.plan.command.application.controller;

import beyond.momentours.common.exception.CommonException;
import beyond.momentours.plan.command.application.dto.PlanDTO;
import beyond.momentours.plan.command.application.mapper.PlanConverter;
import beyond.momentours.plan.command.application.service.PlanService;
import beyond.momentours.plan.command.domain.vo.request.RequestEditPlanVO;
import beyond.momentours.plan.command.domain.vo.request.RequestRegisterPlanVO;
import beyond.momentours.plan.command.domain.vo.response.ResponseEditPlanVO;
import beyond.momentours.plan.command.domain.vo.response.ResponseRegisterPlanVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("commandPlanController")
@RequestMapping("api/plan")
@Slf4j
@RequiredArgsConstructor
public class PlanController {

    private final PlanService planService;
    private final PlanConverter planConverter;

    @PostMapping("/register")
    public ResponseEntity<?> registerPlan(@RequestBody RequestRegisterPlanVO request) {
        log.info("등록 요청된 request 데이터 : {}", request);
        try {
            PlanDTO planDTO = planConverter.fromRegisterVOToDTO(request);
            PlanDTO savePlanDTO = planService.registerPlan(planDTO);
            ResponseRegisterPlanVO response = planConverter.fromDTOToRegisterVO(savePlanDTO);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (CommonException e) {
            log.error("일정 등록 오류: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            log.error("예상치 못한 오류", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("예상치 못한 오류가 발생했습니다");
        }
    }

    @PatchMapping("/edit")
    public ResponseEntity<?> editPlan(@RequestBody RequestEditPlanVO request) {
        log.info("수정 요청된 request 데이터 : {}", request);
        try {
            PlanDTO planDTO = planConverter.fromEditVOToDTO(request);
            PlanDTO editedPlan = planService.editPlan(planDTO);
            ResponseEditPlanVO response = planConverter.fromDTOToEditVO(editedPlan);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (CommonException e) {
            log.error("일정 수정 오류: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            log.error("예상치 못한 오류", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("예상치 못한 오류가 발생했습니다");
        }
    }

    @PatchMapping("/delete")
    public ResponseEntity<?> deletePlan(@PathVariable Long planId) {
        log.info("삭제 요청한 일정 ID : {}", planId);
        try {
            PlanDTO deletedPlan = planService.deletePlan(planId);
            return ResponseEntity.status(HttpStatus.OK).body("성공적으로 삭제되었습니다.");
        } catch (CommonException e) {
            log.error("일정 삭제 오류: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            log.error("예상치 못한 오류", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("예상치 못한 오류가 발생했습니다");
        }
    }
}
