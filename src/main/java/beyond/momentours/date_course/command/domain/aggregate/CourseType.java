package beyond.momentours.date_course.command.domain.aggregate;

import com.fasterxml.jackson.annotation.JsonValue;

public enum CourseType {
    DATE("DATE"),
    TRIP("TRIP");

    private final String courseType;

    CourseType(String courseType) { this.courseType = courseType; }

    @JsonValue
    public String getType() {
        return courseType;
    }
}
