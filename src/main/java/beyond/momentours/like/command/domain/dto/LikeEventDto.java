package beyond.momentours.like.command.domain.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LikeEventDto {
    private Long memberId;
    private Long targetId;
    private String likeType;
    private String action;
    private LocalDateTime timestamp;
}
