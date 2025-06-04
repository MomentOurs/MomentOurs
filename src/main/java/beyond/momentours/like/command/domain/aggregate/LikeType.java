package beyond.momentours.like.command.domain.aggregate;

import com.fasterxml.jackson.annotation.JsonValue;

public enum LikeType {

    MOMENT("MOMENT"),
    DATE_COURSE("DATE_COURSE");

    private final String likeType;

    LikeType(String likeType) { this.likeType = likeType; }

    @JsonValue
    public String getType() {
        return likeType;
    }
}
