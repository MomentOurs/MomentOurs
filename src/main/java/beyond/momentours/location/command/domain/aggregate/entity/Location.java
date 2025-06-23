package beyond.momentours.location.command.domain.aggregate.entity;

import beyond.momentours.location.command.domain.aggregate.LocationStatus;
import org.locationtech.jts.geom.Point;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

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

    @Column(name = "location_name", nullable = false)
    private String locationName;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "image_urls", nullable = false)
    private String imageUrls;

    @Column(name = "location_status", nullable = false, columnDefinition = "BOOLEAN DEFAULT true")
    private LocationStatus locationStatus;

    @Column(name = "description")
    private String description;

    @Column(name = "rating", precision = 2)
    private Double rating;

    @Column(name = "is_open")
    private Boolean isOpen;

    @Column(name = "closing_time")
    private LocalDateTime closingTime;

    @Column(name = "geohash")
    private String geohash;

    @Column(name = "location_point", columnDefinition = "POINT", nullable = false)
    private Point locationPoint;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public List<String> getImageUrlList() {
        return imageUrls != null ? List.of(imageUrls.split(",")) : List.of();
    }

    public void setLocationStatus(beyond.momentours.location.command.domain.aggregate.LocationStatus status) {
        this.locationStatus = status;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setClosingTime(LocalDateTime closingTime) {
        this.closingTime = closingTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
}
