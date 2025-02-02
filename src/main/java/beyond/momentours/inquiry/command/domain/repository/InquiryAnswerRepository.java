package beyond.momentours.inquiry.command.domain.repository;

import beyond.momentours.inquiry.command.domain.aggregate.entity.InquiryAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InquiryAnswerRepository extends JpaRepository<InquiryAnswer, Long> {
}
