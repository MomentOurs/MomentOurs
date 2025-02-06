package beyond.momentours.report.command.domain.aggregate;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ProcessStatus {

    PENDING("PENDING"),   // 대기
    APPROVED("APPROVED"), // 승인
    REJECTED("REJECTED"); // 반려

    private final String status;

    ProcessStatus(String status) { this.status = status; }

    @JsonValue
    public String getStatus() {
        return status;
    }
}

