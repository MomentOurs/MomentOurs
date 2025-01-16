package beyond.momentours.couple.command.domain.aggregate.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MatchingCode {
    private String id;
    private Long userId;
    private LocalDateTime createdAt;
    private MatchingStatus matchingStatus;

    // factory method (서비스 계층에서 new 연산자를 사용하지 않도록 캡슐화)
    public static MatchingCode create(Long userId){
        return new MatchingCode(
                UUID.randomUUID().toString(),
                userId,
                LocalDateTime.now(),
                MatchingStatus.PENDING
        );
    }

    public void setMatchingStatus(MatchingStatus matchingStatus) {
        this.matchingStatus = matchingStatus;
    }
}
