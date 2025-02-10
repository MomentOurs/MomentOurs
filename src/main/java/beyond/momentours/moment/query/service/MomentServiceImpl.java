package beyond.momentours.moment.query.service;

import beyond.momentours.common.exception.CommonException;
import beyond.momentours.common.exception.ErrorCode;
import beyond.momentours.moment.query.dto.MomentDTO;
import beyond.momentours.moment.query.repository.MomentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service("queryMomentServiceImpl")
@RequiredArgsConstructor
public class MomentServiceImpl implements MomentService {

    private final MomentMapper momentMapper;

    @Override
    public List<MomentDTO> getMyMoments(Long memberId) {
        return momentMapper.findMyMoments(memberId);
    }

    @Override
    public MomentDTO getMomentById(Long momentId) {
        MomentDTO moment = momentMapper.findMomentById(momentId);
        if (moment==null) throw new CommonException(ErrorCode.NOT_FOUND_MOMENT);
        return moment;
    }

    @Override
    public List<MomentDTO> getMomentsForMap(Long memberId, Double minLat, Double maxLat, Double minLng, Double maxLng) {
        return momentMapper.findMomentsForMap(
                memberId,
                BigDecimal.valueOf(minLat),
                BigDecimal.valueOf(maxLat),
                BigDecimal.valueOf(minLng),
                BigDecimal.valueOf(maxLng)
        );
    }
}
