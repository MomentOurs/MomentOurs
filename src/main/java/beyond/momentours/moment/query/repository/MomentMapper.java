package beyond.momentours.moment.query.repository;

import beyond.momentours.moment.query.vo.ResponseMomentDetailVO;
import beyond.momentours.moment.query.vo.ResponseMomentListItemVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MomentMapper {

    ResponseMomentDetailVO findMomentDetailById(@Param("momentId") Long momentId);

    List<ResponseMomentListItemVO> findMomentsByLocationIdWithFilter(
            @Param("locationId") Long locationId,
            @Param("cursor") Long cursor,
            @Param("size") int size,
            @Param("sort") String sort,
            @Param("isOurs") Boolean isOurs,
            @Param("certifiedOnly") Boolean certifiedOnly,
            @Param("memberId") Long memberId,
            @Param("coupleId") Long coupleId
    );

    boolean existsActiveById(@Param("momentId") Long momentId);
}
