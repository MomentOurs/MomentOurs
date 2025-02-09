package beyond.momentours.randomquestion.command.domain.repository;

import beyond.momentours.randomquestion.command.domain.aggregate.entity.UserRandomQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRandomQuestionRepository extends JpaRepository<UserRandomQuestion, Long> {
}
