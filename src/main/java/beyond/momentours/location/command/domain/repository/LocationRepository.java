package beyond.momentours.location.command.domain.repository;

import beyond.momentours.location.command.domain.aggregate.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, Long> {
    Optional<Location> findByLatitudeAndLongitude(BigDecimal latitude, BigDecimal longitude);
}
