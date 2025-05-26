package beyond.momentours.location.query.repository;

import beyond.momentours.date_course_location.command.domain.vo.DateCourseLocationVO;
import beyond.momentours.location.command.domain.aggregate.entity.Location;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

@Mapper
public interface LocationMapper {
    Location findByLatitudeAndLongitudeAndLocationName(@Param("latitude") BigDecimal latitude, @Param("longitude") BigDecimal longitude, @Param("locationName") String locationName);

    DateCourseLocationVO getLocationById(@Param("locationId") Long locationId, @Param("courseId") Long courseId);

    Location findById(Long locationId);
}
