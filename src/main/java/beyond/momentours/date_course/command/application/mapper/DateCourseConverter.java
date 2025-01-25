package beyond.momentours.date_course.command.application.mapper;

import beyond.momentours.date_course.command.application.dto.DateCourseDTO;
import beyond.momentours.date_course.command.domain.aggregate.entity.DateCourse;
import beyond.momentours.date_course.command.domain.vo.request.RequestCreateDateCourseVO;
import beyond.momentours.date_course.command.domain.vo.response.ResponseCreateDateCourseVO;
import org.springframework.stereotype.Component;

@Component
public class DateCourseConverter {
    public DateCourseDTO fromCreateVOToDTO(RequestCreateDateCourseVO request) {
        return DateCourseDTO.builder()
                .courseTitle(request.getCourseTitle())
                .courseType(request.getCourseType())
                .courseMemo(request.getCourseMemo())
                .courseStartDate(request.getCourseStartDate())
                .courseEndDate(request.getCourseEndDate())
                .build();
    }

    public ResponseCreateDateCourseVO fromDTOToCreateVO(DateCourseDTO saveDateCourseDTO) {
        return ResponseCreateDateCourseVO.builder()
                .courseId(saveDateCourseDTO.getCourseId())
                .courseTitle(saveDateCourseDTO.getCourseTitle())
                .courseType(saveDateCourseDTO.getCourseType())
                .courseMemo(saveDateCourseDTO.getCourseMemo())
                .courseDisclosure(saveDateCourseDTO.getCourseDisclosure())
                .courseLike(saveDateCourseDTO.getCourseLike())
                .courseView(saveDateCourseDTO.getCourseView())
                .courseStatus(saveDateCourseDTO.getCourseStatus())
                .courseStartDate(saveDateCourseDTO.getCourseStartDate())
                .courseEndDate(saveDateCourseDTO.getCourseEndDate())
                .createdAt(saveDateCourseDTO.getCreatedAt())
                .updatedAt(saveDateCourseDTO.getUpdatedAt())
                .memberId(saveDateCourseDTO.getMemberId())
                .build();
    }

    public DateCourse fromDTOToEntity(DateCourseDTO dateCourseDTO) {
        return DateCourse.builder()
                .courseTitle(dateCourseDTO.getCourseTitle())
                .courseType(dateCourseDTO.getCourseType())
                .courseMemo(dateCourseDTO.getCourseMemo())
                .courseDisclosure(dateCourseDTO.getCourseDisclosure())
                .courseStartDate(dateCourseDTO.getCourseStartDate())
                .courseEndDate(dateCourseDTO.getCourseEndDate())
                .memberId(dateCourseDTO.getMemberId())
                .build();
    }

    public DateCourseDTO fromEntityToDTO(DateCourse savedCourse) {
        return DateCourseDTO.builder()
                .courseId(savedCourse.getCourseId())
                .courseTitle(savedCourse.getCourseTitle())
                .courseType(savedCourse.getCourseType())
                .courseMemo(savedCourse.getCourseMemo())
                .courseDisclosure(savedCourse.getCourseDisclosure())
                .courseLike(savedCourse.getCourseLike())
                .courseView(savedCourse.getCourseView())
                .courseStatus(savedCourse.getCourseStatus())
                .courseStartDate(savedCourse.getCourseStartDate())
                .courseEndDate(savedCourse.getCourseEndDate())
                .createdAt(savedCourse.getCreatedAt())
                .updatedAt(savedCourse.getUpdatedAt())
                .memberId(savedCourse.getMemberId())
                .build();
    }
}
