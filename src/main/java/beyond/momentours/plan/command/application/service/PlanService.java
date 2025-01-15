package beyond.momentours.plan.command.application.service;

import beyond.momentours.plan.command.application.dto.PlanDTO;

public interface PlanService {
    PlanDTO registerPlan(PlanDTO planDTO);

    PlanDTO editPlan(PlanDTO planDTO);
}
