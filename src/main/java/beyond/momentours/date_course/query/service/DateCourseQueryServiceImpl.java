package beyond.momentours.date_course.query.service;

import beyond.momentours.common.exception.CommonException;
import beyond.momentours.common.exception.ErrorCode;
import beyond.momentours.date_course.command.application.dto.DateCourseDTO;
import beyond.momentours.date_course.command.application.mapper.DateCourseConverter;
import beyond.momentours.date_course.command.domain.aggregate.entity.DateCourse;
import beyond.momentours.date_course.query.repository.DateCourseMapper;
import beyond.momentours.date_course_location.command.domain.aggregate.entity.DateCourseLocation;
import beyond.momentours.date_course_location.command.domain.vo.DateCourseLocationVO;
import beyond.momentours.date_course_location.query.repository.DateCourseLocationMapper;
import beyond.momentours.location.query.repository.LocationMapper;
import beyond.momentours.member.command.application.dto.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DateCourseQueryServiceImpl implements DateCourseQueryService {

    private final RedisTemplate<String, String> redisTemplate;
    private final DateCourseConverter dateCourseConverter;
    private final DateCourseMapper dateCourseDAO;
    private final DateCourseLocationMapper dateCourseLocationDAO;
    private final LocationMapper locationDAO;

    @Override
    public List<DateCourseDTO> getDateCourses(String sortBy) {
        List<DateCourse> courses = dateCourseDAO.findCoursesWithSorting(sortBy);
        return courses.stream()
                .map(dateCourseConverter::fromEntityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public DateCourseDTO getDateCourse(Long courseId, CustomUserDetails user) {
        DateCourse dateCourse = dateCourseDAO.findActiveById(courseId).orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_DATE_COURSE));

        if (!dateCourse.getMemberId().equals(user.getMemberId())) {
            String key = "course:view:" + courseId;
            redisTemplate.opsForValue().increment(key, 1);
        }

        List<DateCourseLocation> courseLocations = dateCourseLocationDAO.findByCourseId(courseId);
        List<DateCourseLocationVO> locations = courseLocations.stream()
                .map(loc -> locationDAO.getLocationById(loc.getLocationId(), loc.getCourseId()))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());


        DateCourseDTO dateCourseDTO = dateCourseConverter.fromEntityToDTO(dateCourse);
        dateCourseDTO.setLocations(locations);

        return dateCourseDTO;
    }

    @Override
    public List<DateCourseDTO> getCoursesByFolderId(Long folderId) {
        List<DateCourse> courses = dateCourseDAO.findCoursesByFolder_FolderId(folderId);
        return courses.stream()
                .map(dateCourseConverter::fromEntityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<DateCourseDTO> getCoursesByScrapFolderId(Long courseScrapFolderId) {
        List<DateCourse> courses = dateCourseDAO.findCoursesByScrapFolderId(courseScrapFolderId);
        return courses.stream()
                .map(dateCourseConverter::fromEntityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<DateCourseDTO> getCoursesWithoutFolder(Long memberId) {
        List<DateCourse> courses = dateCourseDAO.findCoursesWithoutFolder(memberId);
        return courses.stream()
                .map(dateCourseConverter::fromEntityToDTO)
                .collect(Collectors.toList());
    }

}
