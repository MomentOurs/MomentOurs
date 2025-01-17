package beyond.momentours.plan.command.application.service;

import beyond.momentours.plan.command.application.dto.PlanDTO;
import jakarta.transaction.Transactional;

import java.util.List;

public interface PlanService {
    @Transactional
    PlanDTO createPlan(PlanDTO planDTO);

    @Transactional
    PlanDTO updatePlan(PlanDTO planDTO);

    @Transactional
    PlanDTO deletePlan(Long planId);

    List<PlanDTO> getPlans(int year, int month);

    List<PlanDTO> getPlansByDate(int year, int month, int day);

    PlanDTO getPlanById(Long planId);
}
