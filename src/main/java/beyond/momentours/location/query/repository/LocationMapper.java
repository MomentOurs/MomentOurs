package beyond.momentours.location.query.repository;

import beyond.momentours.date_course_location.command.domain.vo.DateCourseLocationVO;
import beyond.momentours.location.command.domain.aggregate.entity.Location;
import beyond.momentours.location.query.vo.ResponseLocationClusterGroupVO;
import beyond.momentours.location.query.vo.ResponseLocationClusterItemVO;
import beyond.momentours.location.query.vo.ResponseLocationMapVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface LocationMapper {
    Location findByLatitudeAndLongitudeAndLocationName(@Param("latitude") BigDecimal latitude, @Param("longitude") BigDecimal longitude, @Param("locationName") String locationName);

    DateCourseLocationVO getLocationById(@Param("locationId") Long locationId, @Param("courseId") Long courseId);

    Location findById(Long locationId);

    List<ResponseLocationMapVO> findLocationsInBounds(@Param("latitudeMin") BigDecimal latitudeMin, @Param("latitudeMax") BigDecimal latitudeMax, @Param("longitudeMin") BigDecimal longitudeMin, @Param("longitudeMax") BigDecimal longitudeMax);

    List<ResponseLocationClusterItemVO> findLocationsNearCoordinates(@Param("latitude") BigDecimal latitude, @Param("longitude") BigDecimal longitude);

    List<ResponseLocationClusterGroupVO> findGroupedLocationClusters(@Param("round") int round);
}
