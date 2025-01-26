package beyond.momentours.couple.command.application.dto;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class CoupleProfileDTO {
    private Long coupleId;
    private String coupleName;
    private String couplePhoto;
    private LocalDateTime coupleStartDate;
}
