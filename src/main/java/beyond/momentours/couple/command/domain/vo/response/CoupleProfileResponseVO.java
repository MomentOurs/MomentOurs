package beyond.momentours.couple.command.domain.vo.response;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
public class CoupleProfileResponseVO {
    private Long coupleId;
    private String coupleName;
    private String couplePhoto;
    private LocalDateTime coupleStartDate;
}
