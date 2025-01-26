package beyond.momentours.randomquestionanswer.command.domain.repository;

import beyond.momentours.randomquestionanswer.command.domain.aggregate.entity.RQAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RQAnswerRepository extends JpaRepository<RQAnswer,Long> {
}
