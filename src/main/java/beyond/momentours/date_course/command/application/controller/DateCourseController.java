package beyond.momentours.date_course.command.application.controller;

import beyond.momentours.date_course.command.application.mapper.DateCourseConverter;
import beyond.momentours.date_course.command.application.service.DateCourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("commandDateCourseController")
@RequestMapping("api/course")
@Slf4j
@RequiredArgsConstructor
public class DateCourseController {

    private final DateCourseService dateCourseService;
    private final DateCourseConverter dateCourseConverter;

}
