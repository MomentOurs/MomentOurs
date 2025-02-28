package beyond.momentours.date_course_location.query.service;

import beyond.momentours.date_course_location.query.repository.DateCourseLocationMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DateCourseLocationQueryServiceImpl implements DateCourseLocationQueryService{

    private final DateCourseLocationMapper dateCourseLocationMapper;

    @Override
    public boolean hasMomentsInCourse(Long courseId) {
        List<Long> locationIds = dateCourseLocationMapper.findLocationIdsByCourseId(courseId);
        if (locationIds.isEmpty()) return false;

        int count = dateCourseLocationMapper.countMomentsByLocationIds(locationIds);
        return count > 0;
    }
}
