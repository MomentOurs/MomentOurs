package beyond.momentours.plan.command.application.service;

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
        Long memberId = planDAO.findByMemberId(planDTO.getMemberId());
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
}
