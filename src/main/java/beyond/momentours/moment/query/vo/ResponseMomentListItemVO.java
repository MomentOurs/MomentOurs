package beyond.momentours.moment.query.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ResponseMomentListItemVO {
    private Long momentId;
    private String momentTitle;
    private String momentCategory;
    private String locationName;
}