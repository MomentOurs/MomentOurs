package beyond.momentours.couplelog.command.domain.vo.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class CoupleLogRequestVO {
    private Long coupleId;
    private String textContent;
}
