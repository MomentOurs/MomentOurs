package beyond.momentours.blacklist.command.domain.repository;

import beyond.momentours.blacklist.command.domain.aggregate.entity.BlackList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlackListRepository extends JpaRepository<BlackList, Long> {
}
