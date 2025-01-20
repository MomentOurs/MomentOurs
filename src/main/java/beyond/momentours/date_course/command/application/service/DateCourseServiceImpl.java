package beyond.momentours.date_course.command.application.service;

import beyond.momentours.date_course.command.application.mapper.DateCourseConverter;
import beyond.momentours.date_course.command.domain.repository.DateCourseRepository;
import beyond.momentours.date_course.query.repository.DateCourseMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service("commandDateCourseService")
@RequiredArgsConstructor
public class DateCourseServiceImpl implements DateCourseService {

    private final DateCourseRepository dateCourseRepository;
    private final DateCourseConverter dateCourseConverter;
    private final DateCourseMapper dateCourseDAO;

}
