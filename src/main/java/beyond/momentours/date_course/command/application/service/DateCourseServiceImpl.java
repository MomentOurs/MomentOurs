package beyond.momentours.date_course.command.application.service;

import beyond.momentours.date_course.command.application.dto.DateCourseDTO;
import beyond.momentours.date_course.command.application.mapper.DateCourseConverter;
import beyond.momentours.date_course.command.domain.aggregate.entity.DateCourse;
import beyond.momentours.date_course.command.domain.repository.DateCourseRepository;
import beyond.momentours.date_course.query.repository.DateCourseMapper;
import beyond.momentours.member.command.application.dto.CustomUserDetails;
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

    @Override
    public DateCourseDTO createDateCourse(DateCourseDTO dateCourseDTO, CustomUserDetails user) {
        Long memberId = user.getMemberId();
        dateCourseDTO.setMemberId(memberId);

        DateCourse dateCourse = dateCourseConverter.fromDTOToEntity(dateCourseDTO);
        log.info("저장할 데이트 코스 : {}", dateCourse);

        DateCourse savedCourse = dateCourseRepository.save(dateCourse);

        return dateCourseConverter.fromEntityToDTO(savedCourse);
    }
}
