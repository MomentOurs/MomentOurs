package beyond.momentours.moment.query.service;

import beyond.momentours.moment.query.repository.MomentMapper;
import beyond.momentours.moment.query.vo.ResponseMomentDetailVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MomentQueryServiceImpl implements MomentQueryService {

    private final MomentMapper momentMapper;

    public ResponseMomentDetailVO getMomentDetail(Long momentId) {
        return momentMapper.findMomentDetailById(momentId);
    }
}
