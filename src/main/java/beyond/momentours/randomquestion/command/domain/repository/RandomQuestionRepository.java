package beyond.momentours.randomquestion.command.domain.repository;

import beyond.momentours.randomquestion.command.domain.aggregate.entity.RandomQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RandomQuestionRepository extends JpaRepository<RandomQuestion, Long> {
}
