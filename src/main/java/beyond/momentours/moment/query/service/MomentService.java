package beyond.momentours.moment.query.service;

import beyond.momentours.moment.query.dto.MomentDTO;

import java.util.List;

public interface MomentService {
    List<MomentDTO> getMyMoments(Long memberId);

    MomentDTO getMomentById(Long momentId);

    List<MomentDTO> getMomentsForMap(Long memberId, Double minLat, Double maxLat, Double minLng, Double maxLng);
}
