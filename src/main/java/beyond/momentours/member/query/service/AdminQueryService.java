package beyond.momentours.member.query.service;

import beyond.momentours.member.query.vo.response.ResponseLoginHistoryVO;

import java.util.List;

public interface AdminQueryService {
    List<ResponseLoginHistoryVO> getLoginHistory();
}
