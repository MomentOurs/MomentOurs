package beyond.momentours.pre_blacklist.command.domain.repository;

import beyond.momentours.pre_blacklist.command.domain.aggregate.entity.PreBlacklist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PreBlacklistRepository extends JpaRepository<PreBlacklist, Long> {
}
