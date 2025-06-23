package beyond.momentours.moment.common;

import lombok.*;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MomentFilterCondition {
    private Long locationId;
    private Long cursor;
    private int size;
    private String sort;
    private Boolean isOurs;
    private Boolean certifiedOnly;
    private Long memberId;
    private Long coupleId;
}
