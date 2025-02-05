package beyond.momentours.report.command.domain.aggregate;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ReportReason {

    VIOLENCE("Violence"),                       // 폭력성
    OBSCENITY("Obscenity"),                     // 음란
    FALSE_INFORMATION("False Information"),     // 잘못된 정보
    POLITICAL_STATEMENT("Political Statement"), // 정치적 발언
    ADVERTISING("Advertising");                 // 광고성 내용

    private final String reason;

    ReportReason(String reason) { this.reason = reason; }

    @JsonValue
    public String getReason() {
        return reason;
    }
}

