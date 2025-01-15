package beyond.momentours.plan.command.application.service;

import beyond.momentours.plan.command.application.dto.PlanDTO;
import jakarta.transaction.Transactional;

public interface PlanService {
    @Transactional
    PlanDTO registerPlan(PlanDTO planDTO);

    @Transactional
    PlanDTO editPlan(PlanDTO planDTO);

    @Transactional
    PlanDTO deletePlan(Long planId);
}
