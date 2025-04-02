package beyond.momentours.plan.command.domain.aggregate.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum PlanType {
    PERSONAL("PERSONAL"),
    COUPLE("COUPLE"),
    PERSONAL_TRIP("PERSONAL_TRIP"),
    COUPLE_TRIP("COUPLE_TRIP");

    private final String planType;

    PlanType(String planType) { this.planType = planType; }

    @JsonValue
    public String getType() {
        return planType;
    }
}
