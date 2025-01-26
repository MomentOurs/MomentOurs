package beyond.momentours.date_course_location.command.application.service;

import beyond.momentours.date_course_location.command.domain.vo.DateCourseLocationVO;

import java.util.List;

public interface DateCourseLocationService {
    void createDateCourseLocations(Long courseId, List<DateCourseLocationVO> locations);
}
