package beyond.momentours.couple.query.repository;

import beyond.momentours.couple.command.domain.aggregate.entity.CoupleList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CoupleMapper {
    CoupleList getCoupleByCoupleId(Long coupleId);

    CoupleList getCoupleByMemberId(Long memberId);

    Long getCoupleIdByMemberId(Long memberId);

    List<Long> getAllCoupleIds();

    List<Long> getMemberIdsByCoupleId(@Param("coupleId") Long coupleId);
}
