package beyond.momentours.date_course.query.service;

import beyond.momentours.date_course.command.application.dto.DateCourseDTO;
import beyond.momentours.member.command.application.dto.CustomUserDetails;

import java.util.List;

public interface DateCourseQueryService {
    List<DateCourseDTO> getDateCourses(String sortBy);

    DateCourseDTO getDateCourse(Long courseId, CustomUserDetails user);

    List<DateCourseDTO> getCoursesByMemberId(CustomUserDetails user);
}
