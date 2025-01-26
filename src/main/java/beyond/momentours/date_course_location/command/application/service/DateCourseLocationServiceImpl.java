package beyond.momentours.date_course_location.command.application.service;

import beyond.momentours.common.exception.CommonException;
import beyond.momentours.common.exception.ErrorCode;
import beyond.momentours.date_course_location.command.domain.aggregate.entity.DateCourseLocation;
import beyond.momentours.date_course_location.command.domain.repository.DateCourseLocationRepository;
import beyond.momentours.date_course_location.command.domain.vo.DateCourseLocationVO;
import beyond.momentours.location.command.application.dto.LocationDTO;
import beyond.momentours.location.command.application.service.LocationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service("commandDateCourseLocationService")
@RequiredArgsConstructor
public class DateCourseLocationServiceImpl implements DateCourseLocationService {

    private final LocationService locationService;
    private final DateCourseLocationRepository dateCourseLocationRepository;

    @Transactional
    @Override
    public void createDateCourseLocations(Long courseId, List<DateCourseLocationVO> locations) {
        try {
            if (locations != null && !locations.isEmpty()) {
                for (DateCourseLocationVO locationDTO : locations) {
                    LocationDTO location = locationService.findOrCreateLocation(locationDTO.getLocationName(), locationDTO.getLatitude(), locationDTO.getLongitude());

                    DateCourseLocation courseLocation = DateCourseLocation.builder()
                            .courseId(courseId)
                            .locationId(location.getLocationId())
                            .sequence(locationDTO.getSequence())
                            .courseLocationStatus(true)
                            .build();

                    dateCourseLocationRepository.save(courseLocation);
                    log.info("데이트 코스 장소 매핑 저장: {}", courseLocation);
                }
            }
        } catch (Exception e) {
            log.error("데이트 코스 장소 저장 중 오류 발생", e);
            throw new CommonException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}
