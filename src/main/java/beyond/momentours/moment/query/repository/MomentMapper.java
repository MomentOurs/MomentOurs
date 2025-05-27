package beyond.momentours.moment.query.repository;

import beyond.momentours.location.query.vo.ResponseLocationMomentPageVO;
import beyond.momentours.moment.common.MomentFilterCondition;
import beyond.momentours.moment.query.vo.ResponseMomentDetailVO;
import beyond.momentours.moment.query.vo.ResponseMomentListItemVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MomentMapper {
    ResponseMomentDetailVO findMomentDetailById(@Param("momentId") Long momentId);

    List<ResponseMomentListItemVO> findMomentsByLocationIdWithFilter(Long locationId, Long cursor, int size, String sort, Boolean onlyMine, Boolean certifiedOnly, Long memberId);
}
