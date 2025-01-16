package beyond.momentours.couple.command.domain.repository;

import beyond.momentours.couple.command.domain.aggregate.entity.CoupleList;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CoupleRepository extends JpaRepository<CoupleList, Long> {
    @Query("SELECT c FROM CoupleList c " +
            "WHERE (c.memberId1 = :memberId OR c.memberId2 = :memberId) " +
            "AND c.coupleStatus = true")
    Optional<CoupleList> findActiveCoupleByMemberId(@Param("memberId") Long memberId);
    Optional<CoupleList> findCoupleListByCoupleId(Long coupleId);
}
