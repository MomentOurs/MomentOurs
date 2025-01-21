package beyond.momentours.couplelog.command.domain.aggregate.session;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EditSessionInfo implements Serializable {
    private Long memberId;
    private LocalDateTime startTime;
    private CursorPosition cursorPosition;

    public EditSessionInfo(Long memberId) {
        this.memberId = memberId;
        this.startTime = LocalDateTime.now();
        this.cursorPosition = new CursorPosition(0, 0); // 초기 시작 위치
    }
}
