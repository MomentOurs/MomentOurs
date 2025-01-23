package beyond.momentours.announcement.command.domain.repository;

import beyond.momentours.announcement.command.domain.aggregate.entity.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {

}
