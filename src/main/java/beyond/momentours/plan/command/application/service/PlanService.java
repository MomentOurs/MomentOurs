package beyond.momentours.plan.command.application.service;

import beyond.momentours.member.command.application.dto.CustomUserDetails;
import beyond.momentours.plan.command.application.dto.PlanDTO;
import jakarta.transaction.Transactional;

import java.util.List;

public interface PlanService {
    @Transactional
    PlanDTO createPlan(PlanDTO planDTO, CustomUserDetails user);

    @Transactional
    PlanDTO updatePlan(PlanDTO planDTO, CustomUserDetails user);

    @Transactional
    PlanDTO deletePlan(Long planId, CustomUserDetails user);

    List<PlanDTO> getPlans(int year, int month, CustomUserDetails user);

    List<PlanDTO> getPlansByDate(int year, int month, int day, CustomUserDetails user);

    PlanDTO getPlanById(Long planId);
}
