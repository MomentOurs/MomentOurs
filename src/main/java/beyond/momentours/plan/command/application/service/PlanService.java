package beyond.momentours.plan.command.application.service;

import beyond.momentours.plan.command.application.dto.PlanDTO;
import jakarta.transaction.Transactional;

import java.util.List;

public interface PlanService {
    @Transactional
    PlanDTO registerPlan(PlanDTO planDTO);

    @Transactional
    PlanDTO editPlan(PlanDTO planDTO);

    @Transactional
    PlanDTO deletePlan(Long planId);

    List<PlanDTO> getSchedules(int year, int month);
}
