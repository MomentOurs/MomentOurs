package beyond.momentours.comment.command.domain.aggregate;

import com.fasterxml.jackson.annotation.JsonValue;

public enum CommentType {

    COUPLE_LOG("COUPLE_LOG"),
    QUESTION("QUESTION");

    private final String commentType;

    CommentType(String commentType) { this.commentType = commentType; }

    @JsonValue
    public String getType() {
        return commentType;
    }
}
