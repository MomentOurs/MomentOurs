package beyond.momentours.plan.command.application.mapper;

import beyond.momentours.plan.command.application.dto.PlanDTO;
import beyond.momentours.plan.command.domain.aggregate.entity.Plan;
import beyond.momentours.plan.command.domain.vo.request.RequestUpdatePlanVO;
import beyond.momentours.plan.command.domain.vo.request.RequestCreatePlanVO;
import beyond.momentours.plan.command.domain.vo.response.ResponseGetPlanVO;
import beyond.momentours.plan.command.domain.vo.response.ResponseUpdatePlanVO;
import beyond.momentours.plan.command.domain.vo.response.ResponseCreatePlanVO;
import org.springframework.stereotype.Component;

@Component
public class PlanConverter {
    public PlanDTO fromCreateVOToDTO(RequestCreatePlanVO registerPlanVO) {
        return PlanDTO.builder()
                .planType(registerPlanVO.getPlanType())
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
                .planType(savePlanDTO.getPlanType())
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
                .planType(planDTO.getPlanType())
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
                .planType(plan.getPlanType())
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
                .planId(editPlanVO.getPlanId())
                .planType(editPlanVO.getPlanType())
                .planTitle(editPlanVO.getPlanTitle())
                .planContent(editPlanVO.getPlanContent())
                .planStartDate(editPlanVO.getPlanStartDate())
                .planEndDate(editPlanVO.getPlanEndDate())
                .planReminderDatetime(editPlanVO.getPlanReminderDatetime())
                .memberId(editPlanVO.getMemberId())
                .coupleId(editPlanVO.getCoupleId())
                .build();
    }

    public ResponseUpdatePlanVO fromDTOToUpdateVO(PlanDTO editedPlan) {
        return ResponseUpdatePlanVO.builder()
                .planId(editedPlan.getPlanId())
                .planType(editedPlan.getPlanType())
                .planTitle(editedPlan.getPlanTitle())
                .planContent(editedPlan.getPlanContent())
                .planStartDate(editedPlan.getPlanStartDate())
                .planEndDate(editedPlan.getPlanEndDate())
                .planReminderDatetime(editedPlan.getPlanReminderDatetime())
                .updatedAt(editedPlan.getUpdatedAt())
                .build();
    }

    public ResponseGetPlanVO fromDTOToGetVO(PlanDTO dto) {
        return ResponseGetPlanVO.builder()
                .planId(dto.getPlanId())
                .planType(dto.getPlanType())
                .planTitle(dto.getPlanTitle())
                .planContent(dto.getPlanContent())
                .planStartDate(dto.getPlanStartDate())
                .planEndDate(dto.getPlanEndDate())
                .planReminderDatetime(dto.getPlanReminderDatetime())
                .planStatus(dto.getPlanStatus())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .courseId(dto.getCourseId())
                .build();
    }

}
