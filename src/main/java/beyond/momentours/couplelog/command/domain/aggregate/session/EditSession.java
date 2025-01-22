package beyond.momentours.couplelog.command.domain.aggregate.session;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EditSession {
    // 편집 세의 상태를 나타내는 enum
    public enum Status {
        ACTIVE,     // 현재 편집 중
        INACTIVE,   // 일시적으로 비활성
        COMPLETE    // 편집 완료
    }

    private Long couplelogId;   // 편집 중인 커플로그 id
    private Long memberId;      // 작성자 id
    private String currentContent;  // 현재 편집 중인 내용
    private LocalDateTime startTime;    // 세션 시작 시간
    private LocalDateTime lastActiveTime;   // 마지막 활동 시간
    private Status status;              // 현재 세션 상태
    private List<EditOperation> operations; // 편집 작업 이력

    @Builder
    public EditSession(Long couplelogId, Long memberId, String content) {
        this.couplelogId = couplelogId;
        this.memberId = memberId;
        this.currentContent = content;
        this.startTime = LocalDateTime.now();
        this.lastActiveTime = LocalDateTime.now();
        this.status = Status.ACTIVE;
        this.operations = new ArrayList<>();
    }

    // 세션이 아직 유효한지 검사(30분 유효 기간)
    public boolean isValid() {
        return Status.ACTIVE.equals(status) &&
                Duration.between(lastActiveTime, LocalDateTime.now()).toMinutes() < 30;
    }

    // 새로운 편집 추가
    public void addOperation(EditOperation operation) {
        operations.add(operation);
        this.lastActiveTime = LocalDateTime.now();
        updateContent(operation);
    }

    private void updateContent(EditOperation operation) {
        if (operation.getOperationType() == EditOperation.OperationType.INSERT) {
            String before = currentContent.substring(0, operation.getPosition());
            String after = currentContent.substring(operation.getPosition());
            this.currentContent = before + operation.getContent() + after;
        } else {
            String before = currentContent.substring(0, operation.getPosition());
            String after = currentContent.substring(operation.getPosition() + operation.getContent().length());
            this.currentContent = before + after;
        }
    }

    // 세션 종료
    public void complete() {
        this.status = Status.COMPLETE;
    }
    // 세션 비활성화
    public void deactivate() {
        this.status = Status.INACTIVE;
    }

    // 세션 활성화
    public void activate() {
        this.status = Status.ACTIVE;
        this.lastActiveTime = LocalDateTime.now();
    }
}
