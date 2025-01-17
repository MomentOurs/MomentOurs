package beyond.momentours.plan.command.application.mapper;

import beyond.momentours.plan.command.application.dto.PlanDTO;
import beyond.momentours.plan.command.domain.aggregate.entity.Plan;
import beyond.momentours.plan.command.domain.vo.request.RequestUpdatePlanVO;
import beyond.momentours.plan.command.domain.vo.request.RequestCreatePlanVO;
import beyond.momentours.plan.command.domain.vo.response.ResponseEditPlanVO;
import beyond.momentours.plan.command.domain.vo.response.ResponseCreatePlanVO;
import org.springframework.stereotype.Component;

@Component
public class PlanConverter {
    public PlanDTO fromCreateVOToDTO(RequestCreatePlanVO registerPlanVO) {
        return PlanDTO.builder()
                .planTitle(registerPlanVO.getPlanTitle())
                .planContent(registerPlanVO.getPlanContent())
                .planStartDate(registerPlanVO.getPlanStartDate())
                .planEndDate(registerPlanVO.getPlanEndDate())
                .planReminderDatetime(registerPlanVO.getPlanReminderDatetime())
                .courseId(registerPlanVO.getCourseId())
                .build();
    }

    public ResponseCreatePlanVO fromDTOToCreateVO(PlanDTO savePlanDTO) {
        return ResponseCreatePlanVO.builder()
                .planTitle(savePlanDTO.getPlanTitle())
                .planContent(savePlanDTO.getPlanContent())
                .planStartDate(savePlanDTO.getPlanStartDate())
                .planEndDate(savePlanDTO.getPlanEndDate())
                .planReminderDatetime(savePlanDTO.getPlanReminderDatetime())
                .coupleId(savePlanDTO.getCoupleId())
                .courseId(savePlanDTO.getCourseId())
                .build();
    }

    public Plan fromDTOToEntity(PlanDTO planDTO, Long coupleId) {
        return Plan.builder()
                .planTitle(planDTO.getPlanTitle())
                .planContent(planDTO.getPlanContent())
                .planStartDate(planDTO.getPlanStartDate())
                .planEndDate(planDTO.getPlanEndDate())
                .planReminderDatetime(planDTO.getPlanReminderDatetime())
                .memberId(planDTO.getMemberId())
                .coupleId(coupleId)
                .courseId(planDTO.getCourseId())
                .build();
    }

    public PlanDTO fromEntityToDTO(Plan plan) {
        return PlanDTO.builder()
                .planId(plan.getPlanId())
                .planTitle(plan.getPlanTitle())
                .planContent(plan.getPlanContent())
                .planStartDate(plan.getPlanStartDate())
                .planEndDate(plan.getPlanEndDate())
                .planReminderDatetime(plan.getPlanReminderDatetime())
                .createdAt(plan.getCreatedAt())
                .updatedAt(plan.getUpdatedAt())
                .memberId(plan.getMemberId())
                .coupleId(plan.getCoupleId())
                .courseId(plan.getCourseId())
                .build();
    }

    public PlanDTO fromUpdateVOToDTO(RequestUpdatePlanVO editPlanVO) {
        return PlanDTO.builder()
                .planTitle(editPlanVO.getPlanTitle())
                .planContent(editPlanVO.getPlanContent())
                .planStartDate(editPlanVO.getPlanStartDate())
                .planEndDate(editPlanVO.getPlanEndDate())
                .planReminderDatetime(editPlanVO.getPlanReminderDatetime())
                .courseId(editPlanVO.getCourseId())
                .build();
    }

    public ResponseEditPlanVO fromDTOToUpdateVO(PlanDTO editedPlan) {
        return ResponseEditPlanVO.builder()
                .planTitle(editedPlan.getPlanTitle())
                .planContent(editedPlan.getPlanContent())
                .planStartDate(editedPlan.getPlanStartDate())
                .planEndDate(editedPlan.getPlanEndDate())
                .planReminderDatetime(editedPlan.getPlanReminderDatetime())
                .updatedAt(editedPlan.getUpdatedAt())
                .build();
    }
}
