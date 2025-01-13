package beyond.momentours.plan.command.application.service;

import beyond.momentours.mapper.PlanMapper;
import beyond.momentours.plan.command.application.dto.PlanDTO;
import beyond.momentours.plan.command.domain.aggregate.entity.Plan;
import beyond.momentours.plan.command.domain.repository.PlanRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service("commandPlanService")
@RequiredArgsConstructor
public class PlanServiceImpl implements PlanService {

    private final PlanRepository planRepository;
    private final PlanMapper planDAO;
    private final beyond.momentours.plan.command.application.mapper.PlanMapper planMapper;

    @Override
    public PlanDTO registerPlan(PlanDTO planDTO) {
        Long memberId = planDAO.findByMemberId(planDTO.getMemberId());
        Long coupleId = planDAO.findByCoupleId(memberId);

        log.info("memberId : {} , coupleId : {}", memberId, coupleId);

        Plan plan = planMapper.fromDTOToEntity(planDTO, coupleId);

        if (planDTO.getCourseId() != null) {
            Long courseId = planDAO.findByCourseId(planDTO.getCourseId());
            log.info("courseId : {}", courseId);

            plan.setCourseId(plan, courseId);
        }
        log.info("저장 전 plan : {}", plan);

        plan.register(plan);
        planRepository.save(plan);
        log.info("저장 후 plan : {}", plan);

        PlanDTO result = planMapper.fromEntityToDTO(plan);

        return result;
    }
}
