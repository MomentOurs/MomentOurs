package beyond.momentours.location.command.domain.aggregate;

import com.fasterxml.jackson.annotation.JsonValue;

public enum LocationStatus {
    UNCHANGED("UNCHANGED"),
    RELOCATION("RELOCATION"),
    CLOSED("CLOSED");

    private final String locationStatus;

    LocationStatus(String locationStatus) { this.locationStatus = locationStatus; }

    @JsonValue
    public String getLocationStatus() {
        return locationStatus;
    }
}
