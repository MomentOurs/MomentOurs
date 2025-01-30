package beyond.momentours.inquiry.command.domain.repository;

import beyond.momentours.inquiry.command.domain.aggregate.entity.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InquiryRepository extends JpaRepository<Inquiry, Long> {
}
