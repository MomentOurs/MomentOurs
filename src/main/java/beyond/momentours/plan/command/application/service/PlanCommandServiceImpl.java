package beyond.momentours.plan.command.application.service;

import beyond.momentours.common.exception.CommonException;
import beyond.momentours.common.exception.ErrorCode;
import beyond.momentours.member.command.application.dto.CustomUserDetails;
import beyond.momentours.plan.command.application.mapper.PlanConverter;
import beyond.momentours.plan.command.application.dto.PlanDTO;
import beyond.momentours.plan.command.domain.aggregate.PlanType;
import beyond.momentours.plan.command.domain.aggregate.entity.Plan;
import beyond.momentours.plan.command.domain.repository.PlanRepository;
import beyond.momentours.plan.query.repository.PlanMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlanCommandServiceImpl implements PlanCommandService {

    private final PlanRepository planRepository;
    private final PlanConverter planConverter;
    private final PlanMapper planDAO;

    @Override
    public PlanDTO createPlan(PlanDTO planDTO, CustomUserDetails user) {
        Long memberId = user.getMemberId();
        Long coupleId = null;

        if (planDTO.getPlanType() == PlanType.COUPLE || planDTO.getPlanType() == PlanType.COUPLE_TRIP) {
            coupleId = planDAO.findByCoupleId(memberId);
            if (coupleId == null) throw new CommonException(ErrorCode.NOT_FOUND_COUPLE);
            memberId = null;
        } else if (planDTO.getPlanType() == PlanType.PERSONAL || planDTO.getPlanType() == PlanType.PERSONAL_TRIP) {
            coupleId = null;
        }

        log.info("planType: {}, memberId: {}, coupleId: {}", planDTO.getPlanType(), memberId, coupleId);

        planDTO.setMemberId(memberId);
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

        return planConverter.fromEntityToDTO(plan);
    }

    @Override
    public PlanDTO updatePlan(PlanDTO planDTO, CustomUserDetails user) {
        Long memberId = user.getMemberId();
        planDTO.setMemberId(memberId);

        Plan existingPlan = planRepository.findById(planDTO.getPlanId()).orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_PLAN));
        log.info("기존 Plan 데이터: {}", existingPlan);

        Long coupleId = planDAO.findByCoupleId(memberId);
        log.info("memberId : {} , coupleId : {}", memberId, coupleId);

        if (!existingPlan.getCoupleId().equals(coupleId)) {
            log.error("수정 권한 없음 : 요청한 사용자 ID: {}, 요청한 사용자의 커플 ID: {}, Plan 소유자 ID: {}, Plan 소유자의 커플 ID: {}", memberId, coupleId, existingPlan.getMemberId(), existingPlan.getCoupleId());
            throw new CommonException(ErrorCode.ACCESS_DENIED);
        }

        updatePlan(planDTO, existingPlan);
        log.info("수정 후 Plan : {}", existingPlan);

        planRepository.save(existingPlan);

        return planConverter.fromEntityToDTO(existingPlan);
    }

    @Override
    public PlanDTO deactivatePlan(Long planId, CustomUserDetails user) {
        Plan existingPlan = planRepository.findById(planId).orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_PLAN));
        log.info("삭제 요청된 Plan 데이터: {}", existingPlan);

        Long memberId = user.getMemberId();
        Long coupleId = planDAO.findByCoupleId(memberId);
        log.info("memberId : {} , coupleId : {}", memberId, coupleId);

        if (!existingPlan.getCoupleId().equals(coupleId)) {
            log.error("삭제 권한 없음 : 요청한 사용자 ID: {}, 요청한 사용자의 커플 ID: {}, Plan 소유자 ID: {}, Plan 소유자의 커플 ID: {}", memberId, coupleId, existingPlan.getMemberId(), existingPlan.getCoupleId());
            throw new CommonException(ErrorCode.ACCESS_DENIED);
        }

        existingPlan.updateStatus(false);
        log.info("상태 변경 후 Plan : {}", existingPlan);

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
