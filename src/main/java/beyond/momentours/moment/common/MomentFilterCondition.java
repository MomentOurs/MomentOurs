package beyond.momentours.moment.common;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MomentFilterCondition {
    private Long locationId;
    private Long cursor;
    private int size;
    private String sort;
    private Boolean onlyMine;
    private Boolean certifiedOnly;
    private Long memberId;
}
