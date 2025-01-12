package beyond.momentours.moment.command.domain.repository;

import beyond.momentours.moment.command.domain.aggregate.entity.Moment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MomentRepository extends JpaRepository<Moment, Long> {
}
