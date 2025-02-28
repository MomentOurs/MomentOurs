package beyond.momentours.date_course.query.service;

import beyond.momentours.common.exception.CommonException;
import beyond.momentours.common.exception.ErrorCode;
import beyond.momentours.date_course.command.application.dto.DateCourseDTO;
import beyond.momentours.date_course.command.application.mapper.DateCourseConverter;
import beyond.momentours.date_course.command.domain.aggregate.entity.DateCourse;
import beyond.momentours.date_course.query.repository.DateCourseMapper;
import beyond.momentours.member.command.application.dto.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DateCourseQueryServiceImpl implements DateCourseQueryService {

    private final RedisTemplate<String, String> redisTemplate;
    private final DateCourseConverter dateCourseConverter;
    private final DateCourseMapper dateCourseDAO;

    @Override
    public List<DateCourseDTO> getDateCourses(String sortBy) {
        List<DateCourse> courses = dateCourseDAO.findCoursesWithSorting(sortBy);
        return courses.stream()
                .map(dateCourseConverter::fromEntityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public DateCourseDTO getDateCourse(Long courseId, CustomUserDetails user) {
        String key = "course:view:" + courseId;
        redisTemplate.opsForValue().increment(key, 1);

        DateCourse dateCourse = dateCourseDAO.findActiveById(courseId).orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_DATE_COURSE));
        if (!dateCourse.getCourseDisclosure() && !dateCourse.getMemberId().equals(user.getMemberId())) throw new CommonException(ErrorCode.ACCESS_DENIED);

        return dateCourseConverter.fromEntityToDTO(dateCourse);
    }

    @Override
    public List<DateCourseDTO> getCoursesByMemberId(CustomUserDetails user) {
        Long memberId = user.getMemberId();
        List<DateCourse> courses = dateCourseDAO.findCoursesByMemberId(memberId);
        return courses.stream()
                .map(dateCourseConverter::fromEntityToDTO)
                .collect(Collectors.toList());
    }
}
