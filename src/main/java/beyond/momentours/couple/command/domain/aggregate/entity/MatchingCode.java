package beyond.momentours.couple.command.domain.aggregate.entity;

import beyond.momentours.common.exception.CommonException;
import beyond.momentours.common.exception.ErrorCode;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MatchingCode {
    private String id;
    private Long memberId;
    private LocalDateTime createdAt;
    private MatchingStatus matchingStatus;

    // factory method (서비스 계층에서 new 연산자를 사용하지 않도록 캡슐화)
    public static MatchingCode create(Long memberId){
        return new MatchingCode(
                UUID.randomUUID().toString(),
                memberId,
                LocalDateTime.now(),
                MatchingStatus.PENDING
        );
    }

    public void markAsUsed() {
        if(this.matchingStatus == MatchingStatus.PENDING)
            this.matchingStatus = MatchingStatus.USED;
        else throw new CommonException(ErrorCode.USED_CODE_REQUEST);
    }
}
