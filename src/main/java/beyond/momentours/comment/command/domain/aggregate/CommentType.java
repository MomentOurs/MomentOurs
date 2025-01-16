package beyond.momentours.comment.command.domain.aggregate;

import com.fasterxml.jackson.annotation.JsonValue;

public enum CommentType {

    COUPLE_LOG("COUPLE_LOG"),
    MOMENT("MOMENT");

    private final String commentType;

    CommentType(String commentType) { this.commentType = commentType; }

    // JSON으로 직렬화할 때 사용할 값 지정
    // Enum 직렬화될 때 getType() 메서드가 반환하는 값이 JSON으로 변환됨
    @JsonValue
    public String getType() {
        return commentType;
    }
}
