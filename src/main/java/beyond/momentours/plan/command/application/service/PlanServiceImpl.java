package beyond.momentours.plan.command.application.service;

import beyond.momentours.common.exception.CommonException;
import beyond.momentours.common.exception.ErrorCode;
import beyond.momentours.member.command.application.dto.CustomUserDetails;
import beyond.momentours.plan.command.application.mapper.PlanConverter;
import beyond.momentours.plan.command.application.dto.PlanDTO;
import beyond.momentours.plan.command.domain.aggregate.entity.Plan;
import beyond.momentours.plan.command.domain.repository.PlanRepository;
import beyond.momentours.plan.query.repository.PlanMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service("commandPlanService")
@RequiredArgsConstructor
public class PlanServiceImpl implements PlanService {

    private final PlanRepository planRepository;
    private final PlanConverter planConverter;
    private final PlanMapper planDAO;

    @Override
    public PlanDTO createPlan(PlanDTO planDTO, CustomUserDetails user) {
        Long memberId = user.getMemberId();
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
    public PlanDTO deletePlan(Long planId, CustomUserDetails user) {
        // Plan 조회
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

    @Override
    public List<PlanDTO> getPlans(int year, int month, CustomUserDetails user) {
        LocalDateTime planStartDate = LocalDateTime.of(year, month, 1, 0, 0, 0);
        LocalDateTime planEndDate = planStartDate.withDayOfMonth(planStartDate.toLocalDate().lengthOfMonth())
                .withHour(23)
                .withMinute(59)
                .withSecond(59);

        log.info("스케줄 조회 - 시작 날짜: {}, 종료 날짜: {}", planStartDate, planEndDate);

        Long memberId = user.getMemberId();
        Long coupleId = planDAO.findByCoupleId(memberId);
        log.info("memberId : {} , coupleId : {}", memberId, coupleId);

        List<Plan> plans = planDAO.findByCoupleIdAndDateRange(coupleId, planStartDate, planEndDate);

        return plans.stream()
                .map(planConverter::fromEntityToDTO)
                .toList();
    }

    @Override
    public List<PlanDTO> getPlansByDate(int year, int month, int day, CustomUserDetails user) {
        LocalDateTime selectedDateStart = LocalDateTime.of(year, month, day, 0, 0, 0);
        LocalDateTime selectedDateEnd = LocalDateTime.of(year, month, day, 23, 59, 59);

        log.info("특정 날짜 일정 조회 - 시작: {}, 종료: {}", selectedDateStart, selectedDateEnd);

        Long memberId = user.getMemberId();
        Long coupleId = planDAO.findByCoupleId(memberId);

        List<Plan> plans = planDAO.findByDate(coupleId, selectedDateStart, selectedDateEnd);

        return plans.stream()
                .map(planConverter::fromEntityToDTO)
                .toList();
    }

    @Override
    public PlanDTO getPlanById(Long planId) {
        Plan plan = planRepository.findById(planId).orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_PLAN));

        return planConverter.fromEntityToDTO(plan);
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
