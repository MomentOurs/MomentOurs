package beyond.momentours.plan.command.application.service;

import beyond.momentours.common.exception.CommonException;
import beyond.momentours.common.exception.ErrorCode;
import beyond.momentours.plan.command.application.mapper.PlanConverter;
import beyond.momentours.plan.command.application.dto.PlanDTO;
import beyond.momentours.plan.command.domain.aggregate.entity.Plan;
import beyond.momentours.plan.command.domain.repository.PlanRepository;
import beyond.momentours.plan.query.repository.PlanMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service("commandPlanService")
@RequiredArgsConstructor
public class PlanServiceImpl implements PlanService {

    private final PlanRepository planRepository;
    private final PlanConverter planConverter;
    private final PlanMapper planDAO;

    @Override
    public PlanDTO registerPlan(PlanDTO planDTO) {
        // 로그인 사용자 ID를 토큰에서 가져오기
        Long memberId = getLoggedInMemberId();; // getLoggedInMemberId 는 로그인한 유저의 memberId를 토큰에서 가져온다는 가정으로 써둔 메서드
        planDTO.setMemberId(memberId);

        Long coupleId = planDAO.findByCoupleId(memberId);
        log.info("memberId : {} , coupleId : {}", memberId, coupleId);

        Plan plan = planConverter.fromDTOToEntity(planDTO, coupleId);

        if (planDTO.getCourseId() != null) {
            Long courseId = planDAO.findByCourseId(planDTO.getCourseId());
            log.info("courseId : {}", courseId);

            plan.setCourseId(plan, courseId);
        }
        log.info("저장 전 plan : {}", plan);

        plan.register(plan);
        planRepository.save(plan);
        log.info("저장 후 plan : {}", plan);

        PlanDTO result = planConverter.fromEntityToDTO(plan);

        return result;
    }

    @Override
    public PlanDTO editPlan(PlanDTO planDTO) {
        Long memberId = getLoggedInMemberId();; // 로그인한 사용자의 ID 가져오기
        planDTO.setMemberId(memberId);

        Plan existingPlan = planRepository.findById(planDTO.getPlanId()).orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_PLAN));
        log.info("기존 Plan 데이터: {}", existingPlan);

        if (!existingPlan.getMemberId().equals(memberId)) {
            log.error("수정 권한 없음 : 요청한 사용자 ID: {}, Plan 소유자 ID: {}", memberId, existingPlan.getMemberId());
            throw new CommonException(ErrorCode.ACCESS_DENIED);
        }

        updatePlan(planDTO, existingPlan);
        log.info("수정 후 Plan : {}", existingPlan);

        planRepository.save(existingPlan);

        return planConverter.fromEntityToDTO(existingPlan);
    }

    private void updatePlan(PlanDTO planDTO, Plan existingPlan) {
        if (planDTO.getPlanTitle() != null) existingPlan.updateTitle(planDTO.getPlanTitle());
        if (planDTO.getPlanContent() != null) existingPlan.updateContent(planDTO.getPlanContent());
        if (planDTO.getPlanStartDate() != null) existingPlan.updateStartDate(planDTO.getPlanStartDate());
        if (planDTO.getPlanEndDate() != null) existingPlan.updateEndDate(planDTO.getPlanEndDate());
        if (planDTO.getPlanReminderDatetime() != null)
            existingPlan.updateReminderDatetime(planDTO.getPlanReminderDatetime());
        if (planDTO.getCourseId() != null) {
            Long courseId = planDAO.findByCourseId(planDTO.getCourseId());
            existingPlan.updateCourseId(courseId);
        }
    }

}
