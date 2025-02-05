package beyond.momentours.report.command.domain.aggregate;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ReportType {

    MOMENT("MOMENT"),
    DATE_COURSE("DATE_COURSE");

    private final String reportType;

    ReportType(String reportType) { this.reportType = reportType; }

    @JsonValue
    public String getReportType() {
        return reportType;
    }
}
