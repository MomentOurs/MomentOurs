package beyond.momentours.plan.query.service;

import beyond.momentours.common.exception.CommonException;
import beyond.momentours.common.exception.ErrorCode;
import beyond.momentours.member.command.application.dto.CustomUserDetails;
import beyond.momentours.plan.command.application.dto.PlanDTO;
import beyond.momentours.plan.command.application.mapper.PlanConverter;
import beyond.momentours.plan.command.domain.aggregate.entity.Plan;
import beyond.momentours.plan.command.domain.repository.PlanRepository;
import beyond.momentours.plan.query.repository.PlanMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlanQueryServiceImpl implements PlanQueryService {

    private final PlanRepository planRepository;
    private final PlanConverter planConverter;
    private final PlanMapper planDAO;

    @Override
    public List<PlanDTO> getPlans(int year, int month, List<String> types, CustomUserDetails user) {
        LocalDateTime planStartDate = LocalDateTime.of(year, month, 1, 0, 0, 0);
        LocalDateTime planEndDate = planStartDate.withDayOfMonth(planStartDate.toLocalDate().lengthOfMonth())
                .withHour(23)
                .withMinute(59)
                .withSecond(59);

        log.info("스케줄 조회 - 시작 날짜: {}, 종료 날짜: {}, type: {}", planStartDate, planEndDate, types);

        Long memberId = user.getMemberId();
        Long coupleId = planDAO.findByCoupleId(memberId);

        List<Plan> plans = planDAO.findByTypeAndDateRange(coupleId, planStartDate, planEndDate, types);

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

        List<Plan> plans = planDAO.findByMemberOrCoupleIdAndDateRange(memberId, coupleId, selectedDateStart, selectedDateEnd);

        return plans.stream()
                .map(planConverter::fromEntityToDTO)
                .toList();
    }

    @Override
    public PlanDTO getPlanById(Long planId) {
        Plan plan = planRepository.findById(planId).orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_PLAN));

        return planConverter.fromEntityToDTO(plan);
    }
}
