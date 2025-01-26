package beyond.momentours.couple.command.domain.vo.request;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
public class CoupleProfileRequestVO {
    // 수정 가능한 항목: 커플명, 커플프로필 사진, 사귄 날짜
    private Long coupleId;
    private String coupleName;
    private String couplePhoto;
    private LocalDateTime coupleStartDate;
}
