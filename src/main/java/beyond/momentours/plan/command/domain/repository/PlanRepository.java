package beyond.momentours.plan.command.domain.repository;

import beyond.momentours.plan.command.domain.aggregate.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Long> {
}
