package beyond.momentours.location.command.domain.aggregate.entity;

import beyond.momentours.location.command.domain.aggregate.LocationStatus;
import org.locationtech.jts.geom.Point;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "tb_location")
@Entity
@ToString
@Builder
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id")
    private Long locationId;

    @Column(name = "latitude", precision = 10, scale = 7)
    private BigDecimal latitude;

    @Column(name = "longitude", precision = 10, scale = 7)
    private BigDecimal longitude;

    @Column(name = "location_name")
    private String locationName;

    @Column(name = "address")
    private String address;

    @Column(name = "location_status")
    private LocationStatus locationStatus;

    @Column(name = "geohash")
    private String geohash;

    @Column(name = "location_point", columnDefinition = "POINT", nullable = false)
    private Point locationPoint;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
