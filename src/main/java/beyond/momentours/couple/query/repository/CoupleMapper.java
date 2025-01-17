package beyond.momentours.couple.query.repository;

import beyond.momentours.couple.command.domain.aggregate.entity.CoupleList;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CoupleMapper {
    CoupleList getCoupleByCoupleId(Long coupleId);
}
