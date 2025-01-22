package beyond.momentours.couplelog.command.domain.aggregate.session;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EditOperation {
    private OperationType operationType;
    private int position;
    private String content;
    private Long timestamp;
    private Long memberId;

    public enum OperationType {
        INSERT, DELETE
    }

    @Builder
    public EditOperation(OperationType operationType, int position, String content, Long timestamp, Long memberId) {
        this.operationType = operationType;
        this.position = position;
        this.content = content;
        this.timestamp = System.currentTimeMillis();
        this.memberId = memberId;
    }
}
