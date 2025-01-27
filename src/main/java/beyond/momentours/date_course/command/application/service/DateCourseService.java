package beyond.momentours.date_course.command.application.service;

import beyond.momentours.date_course.command.application.dto.DateCourseDTO;
import beyond.momentours.member.command.application.dto.CustomUserDetails;
import jakarta.transaction.Transactional;

public interface DateCourseService {
    DateCourseDTO createDateCourse(DateCourseDTO dateCourseDTO, CustomUserDetails user);

    @Transactional
    DateCourseDTO updateDateCourse(DateCourseDTO dateCourseDTO, CustomUserDetails user);

    @Transactional
    void deleteDateCourse(Long courseId, CustomUserDetails user);
}
