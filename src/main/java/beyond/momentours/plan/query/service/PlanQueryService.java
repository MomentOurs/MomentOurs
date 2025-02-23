package beyond.momentours.plan.query.service;

import beyond.momentours.member.command.application.dto.CustomUserDetails;
import beyond.momentours.plan.command.application.dto.PlanDTO;

import java.util.List;

public interface PlanQueryService {
    List<PlanDTO> getPlans(int year, int month, List<String> types, CustomUserDetails user);

    List<PlanDTO> getPlansByDate(int year, int month, int day, CustomUserDetails user);

    PlanDTO getPlanById(Long planId);
}
