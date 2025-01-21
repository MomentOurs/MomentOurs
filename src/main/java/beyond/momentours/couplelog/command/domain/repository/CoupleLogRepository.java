package beyond.momentours.couplelog.command.domain.repository;

import beyond.momentours.couplelog.command.domain.aggregate.entity.CoupleLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoupleLogRepository extends JpaRepository<CoupleLog, Long> {
}
