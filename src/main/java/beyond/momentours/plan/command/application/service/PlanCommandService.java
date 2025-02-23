package beyond.momentours.plan.command.application.service;

import beyond.momentours.member.command.application.dto.CustomUserDetails;
import beyond.momentours.plan.command.application.dto.PlanDTO;
import jakarta.transaction.Transactional;

import java.util.List;

public interface PlanCommandService {
    @Transactional
    PlanDTO createPlan(PlanDTO planDTO, CustomUserDetails user);

    @Transactional
    PlanDTO updatePlan(PlanDTO planDTO, CustomUserDetails user);

    @Transactional
    PlanDTO deactivatePlan(Long planId, CustomUserDetails user);
}
