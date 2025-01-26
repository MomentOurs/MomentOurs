package beyond.momentours.member.command.domain.repository;

import beyond.momentours.member.command.domain.aggregate.entity.LoginHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginHistoryRepository extends JpaRepository<LoginHistory, Long> {
}
