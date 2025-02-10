package beyond.momentours.moment.query.repository;

import beyond.momentours.moment.query.dto.MomentDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface MomentMapper {

    // 내가 쓴 추억 조회
    List<MomentDTO> findMyMoments(@Param("memberId") Long memberId);

    // 특정 Moment 상세 조회
    MomentDTO findMomentById(@Param("momentId") Long momentId);

    // 지도에서 조회 (지도 범위 내 내 추억 + 공개된 타인 추억)
    List<MomentDTO> findMomentsForMap(
            @Param("memberId") Long memberId,
            @Param("minLat") BigDecimal minLat,
            @Param("maxLat") BigDecimal maxLat,
            @Param("minLng") BigDecimal minLng,
            @Param("maxLng") BigDecimal maxLng
    );
}
