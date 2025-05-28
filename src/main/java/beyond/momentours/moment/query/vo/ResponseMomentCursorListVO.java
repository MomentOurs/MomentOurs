package beyond.momentours.moment.query.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class ResponseMomentCursorListVO {
    private List<ResponseMomentListItemVO> moments;
    private Long nextCursor;
    private Boolean hasNext;
}
