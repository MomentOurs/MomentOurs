package beyond.momentours.moment.query.service;

import beyond.momentours.moment.query.vo.ResponseMomentDetailVO;

public interface MomentQueryService {
    ResponseMomentDetailVO getMomentDetail(Long momentId);
}
