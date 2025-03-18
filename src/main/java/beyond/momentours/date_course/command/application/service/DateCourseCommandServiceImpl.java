package beyond.momentours.date_course.command.application.service;

import beyond.momentours.common.exception.CommonException;
import beyond.momentours.common.exception.ErrorCode;
import beyond.momentours.date_course.command.application.dto.DateCourseDTO;
import beyond.momentours.date_course.command.application.mapper.DateCourseConverter;
import beyond.momentours.date_course.command.domain.aggregate.entity.DateCourse;
import beyond.momentours.date_course.command.domain.repository.DateCourseRepository;
import beyond.momentours.date_course.query.repository.DateCourseMapper;
import beyond.momentours.date_course_location.command.application.service.DateCourseLocationCommandService;
import beyond.momentours.date_course_location.query.service.DateCourseLocationQueryService;
import beyond.momentours.member.command.application.dto.CustomUserDetails;
import beyond.momentours.plan.command.application.dto.PlanDTO;
import beyond.momentours.plan.command.application.service.PlanCommandService;
import beyond.momentours.plan.command.domain.aggregate.PlanType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Slf4j
@Service
@RequiredArgsConstructor
public class DateCourseCommandServiceImpl implements DateCourseCommandService {

    private final DateCourseRepository dateCourseRepository;
    private final DateCourseConverter dateCourseConverter;
    private final DateCourseMapper dateCourseDAO;
    private final DateCourseLocationCommandService dateCourseLocationCommandService;
    private final DateCourseLocationQueryService dateCourseLocationQueryService;
    private final PlanCommandService planCommandService;

    @Transactional
    @Override
    public DateCourseDTO createDateCourse(DateCourseDTO dateCourseDTO, CustomUserDetails user) {
        try {
            Long memberId = user.getMemberId();
            dateCourseDTO.setMemberId(memberId);

            DateCourse dateCourse = dateCourseConverter.fromDTOToEntity(dateCourseDTO);
            log.info("저장할 데이트 코스 : {}", dateCourse);
            DateCourse savedCourse = dateCourseRepository.save(dateCourse);

            dateCourseLocationCommandService.createDateCourseLocations(savedCourse.getCourseId(), dateCourseDTO.getLocations());

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
        DateCourse existingCourse = dateCourseDAO.findActiveById(courseId).orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_DATE_COURSE));

        update(dateCourseDTO, existingCourse, courseId);

        DateCourse updatedCourse = dateCourseRepository.save(existingCourse);
        return dateCourseConverter.fromEntityToDTO(updatedCourse);
    }

    @Transactional
    @Override
    public void deleteDateCourse(Long courseId, CustomUserDetails user) {
        boolean exists = dateCourseDAO.existsActiveById(courseId);
        if (!exists) throw new CommonException(ErrorCode.NOT_FOUND_DATE_COURSE);

        DateCourse dateCourse = dateCourseRepository.findById(courseId).orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_DATE_COURSE));
        if (!dateCourse.getMemberId().equals(user.getMemberId())) throw new CommonException(ErrorCode.UNAUTHORIZED_ACCESS);

        dateCourse.deleteCourse(false);
        dateCourse.updateUpdatedAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")));
        dateCourseRepository.save(dateCourse);

        log.info("데이트 코스 Soft Delete 완료: courseId={}, userId={}", courseId, user.getMemberId());
    }

    @Transactional
    @Override
    public void certifyDateCourse(Long courseId, CustomUserDetails user) {
        boolean exists = dateCourseDAO.existsActiveById(courseId);
        if (!exists) throw new CommonException(ErrorCode.NOT_FOUND_DATE_COURSE);

        DateCourse dateCourse = dateCourseDAO.findActiveById(courseId).orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_DATE_COURSE));

        if (!dateCourse.getMemberId().equals(user.getMemberId())) throw new CommonException(ErrorCode.UNAUTHORIZED_ACCESS);

        boolean hasMoment = dateCourseLocationQueryService.hasMomentsInCourse(courseId);

        if (hasMoment) {
            dateCourse.certifyCourse();
            dateCourse.updateUpdatedAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")));
            dateCourseRepository.save(dateCourse);
            log.info("데이트 코스 인증 완료: courseId={}, userId={}", courseId, user.getMemberId());
        } else {
            throw new CommonException(ErrorCode.NOT_FOUND_MOMENT);
        }
    }

    @Transactional
    @Override
    public DateCourseDTO updateCourseSchedule(DateCourseDTO dateCourseDTO, CustomUserDetails user, String planTypeStr) {
        DateCourse dateCourse = dateCourseRepository.findById(dateCourseDTO.getCourseId()).orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_DATE_COURSE));
        if (!dateCourse.getMemberId().equals(user.getMemberId())) throw new CommonException(ErrorCode.UNAUTHORIZED_ACCESS);

        dateCourse.updateSchedule(dateCourseDTO.getCourseStartDate(), dateCourseDTO.getCourseEndDate());
        dateCourseRepository.save(dateCourse);

        log.info("데이트 코스 일정 업데이트 완료: courseId={}, userId={}, start={}, end={}", dateCourse.getCourseId(), user.getMemberId(), dateCourseDTO.getCourseStartDate(), dateCourseDTO.getCourseEndDate());

        PlanType planType = getPlanTypeFromString(planTypeStr);
        PlanDTO planDTO = PlanDTO.builder()
                .planType(planType)
                .planTitle(dateCourseDTO.getCourseTitle())
                .planContent(dateCourseDTO.getCourseTitle() + "데이트 코스 일정")
                .planStartDate(dateCourseDTO.getCourseStartDate())
                .planEndDate(dateCourseDTO.getCourseEndDate())
                .courseId(dateCourseDTO.getCourseId())
                .build();

        planCommandService.createPlan(planDTO, user);
        log.info("데이트 코스에 따른 일정 등록 완료: courseId={}, planType={}", dateCourse.getCourseId(), planType);

        return dateCourseConverter.fromEntityToDTO(dateCourse);
    }

    private PlanType getPlanTypeFromString(String planTypeStr) {
        if (planTypeStr == null) {
            return PlanType.PERSONAL_TRIP;
        }
        try {
            return PlanType.valueOf(planTypeStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new CommonException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    private void update(DateCourseDTO dateCourseDTO, DateCourse existingCourse, Long courseId) {
        existingCourse.updateCourseTitle(dateCourseDTO.getCourseTitle());
        existingCourse.updateCourseType(dateCourseDTO.getCourseType());
        existingCourse.updateCourseDisclosure(dateCourseDTO.getCourseDisclosure());
        existingCourse.updateCourseStartDate(dateCourseDTO.getCourseStartDate());
        existingCourse.updateCourseEndDate(dateCourseDTO.getCourseEndDate());

        if (dateCourseDTO.getLocations() != null) {
            dateCourseLocationCommandService.updateDateCourseLocations(courseId, dateCourseDTO.getLocations());
        }

        existingCourse.updateUpdatedAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")));
    }
}
