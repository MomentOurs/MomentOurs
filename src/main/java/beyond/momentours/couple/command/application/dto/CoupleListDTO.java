package beyond.momentours.couple.command.application.dto;

import beyond.momentours.couple.command.domain.aggregate.entity.CoupleMatchingStatus;
import lombok.*;

import java.time.LocalDateTime;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CoupleListDTO {
    private Long coupleId;
    private String coupleName;
    private String couplePhoto;
    private LocalDateTime coupleStartDate;
    private Boolean coupleStatus;
    private CoupleMatchingStatus coupleMatchingStatus;
    private Long memberId1;
    private Long memberId2;
}
