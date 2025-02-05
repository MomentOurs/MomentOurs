package beyond.momentours.report.command.domain.repository;

import beyond.momentours.report.command.domain.aggregate.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
}
