package beyond.momentours.moment.query.repository;

import beyond.momentours.moment.query.vo.ResponseMomentDetailVO;
import beyond.momentours.moment.query.vo.ResponseMomentListItemVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MomentMapper {
    List<ResponseMomentListItemVO> findMomentsByLocationIdWithCursor(@Param("locationId") Long locationId, @Param("cursor") Long cursor, @Param("size") int size);

    ResponseMomentDetailVO findMomentDetailById(@Param("momentId") Long momentId);

}
