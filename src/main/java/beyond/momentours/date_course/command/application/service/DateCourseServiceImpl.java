package beyond.momentours.date_course.command.application.service;

import beyond.momentours.common.exception.CommonException;
import beyond.momentours.common.exception.ErrorCode;
import beyond.momentours.date_course.command.application.dto.DateCourseDTO;
import beyond.momentours.date_course.command.application.mapper.DateCourseConverter;
import beyond.momentours.date_course.command.domain.aggregate.entity.DateCourse;
import beyond.momentours.date_course.command.domain.repository.DateCourseRepository;
import beyond.momentours.date_course_location.command.application.service.DateCourseLocationService;
import beyond.momentours.member.command.application.dto.CustomUserDetails;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Slf4j
@Service("commandDateCourseService")
@RequiredArgsConstructor
public class DateCourseServiceImpl implements DateCourseService {

    private final DateCourseRepository dateCourseRepository;
    private final DateCourseConverter dateCourseConverter;
    private final DateCourseLocationService dateCourseLocationService;

    @Transactional
    @Override
    public DateCourseDTO createDateCourse(DateCourseDTO dateCourseDTO, CustomUserDetails user) {
        try {
            Long memberId = user.getMemberId();
            dateCourseDTO.setMemberId(memberId);

            DateCourse dateCourse = dateCourseConverter.fromDTOToEntity(dateCourseDTO);
            log.info("저장할 데이트 코스 : {}", dateCourse);
            DateCourse savedCourse = dateCourseRepository.save(dateCourse);

            dateCourseLocationService.createDateCourseLocations(savedCourse.getCourseId(), dateCourseDTO.getLocations());

            log.info("데이트 코스 등록 성공 : {}", savedCourse);
            return dateCourseConverter.fromEntityToDTO(savedCourse);
        } catch (Exception e) {
            log.error("데이트 코스 등록 중 오류 발생", e);
            throw new CommonException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    @Override
    public DateCourseDTO updateDateCourse(DateCourseDTO dateCourseDTO, CustomUserDetails user) {
        Long courseId = dateCourseDTO.getCourseId();
        DateCourse existingCourse = dateCourseRepository.findById(courseId).orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_DATE_COURSE));

        update(dateCourseDTO, existingCourse, courseId);

        DateCourse updatedCourse = dateCourseRepository.save(existingCourse);
        return dateCourseConverter.fromEntityToDTO(updatedCourse);
    }

    private void update(DateCourseDTO dateCourseDTO, DateCourse existingCourse, Long courseId) {
        existingCourse.updateCourseTitle(dateCourseDTO.getCourseTitle());
        existingCourse.updateCourseType(dateCourseDTO.getCourseType());
        existingCourse.updateCourseMemo(dateCourseDTO.getCourseMemo());
        existingCourse.updateCourseDisclosure(dateCourseDTO.getCourseDisclosure());
        existingCourse.updateCourseStartDate(dateCourseDTO.getCourseStartDate());
        existingCourse.updateCourseEndDate(dateCourseDTO.getCourseEndDate());

        if (dateCourseDTO.getLocations() != null) {
            dateCourseLocationService.updateDateCourseLocations(courseId, dateCourseDTO.getLocations());
        }

        existingCourse.updateUpdatedAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")));
    }
}
