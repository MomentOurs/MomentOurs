package beyond.momentours.blacklist.command.application.dto;

import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class BlackListDTO {

    private Long memberId;
    private Boolean blackStatus;
    private LocalDateTime blackDate;
    private LocalDateTime accessibleDate;
    private Integer blacklistDays;
}
