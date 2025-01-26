package beyond.momentours.date_course.command.application.service;

import beyond.momentours.date_course.command.application.dto.DateCourseDTO;
import beyond.momentours.member.command.application.dto.CustomUserDetails;

public interface DateCourseService {
    DateCourseDTO createDateCourse(DateCourseDTO dateCourseDTO, CustomUserDetails user);
}
