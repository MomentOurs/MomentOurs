package beyond.momentours.date_course.command.application.mapper;

import beyond.momentours.date_course.command.application.dto.DateCourseDTO;
import beyond.momentours.date_course.command.domain.aggregate.entity.DateCourse;
import beyond.momentours.date_course.command.domain.vo.request.RequestCreateDateCourseVO;
import beyond.momentours.date_course.command.domain.vo.request.RequestUpdateDateCourseVO;
import beyond.momentours.date_course.command.domain.vo.response.ResponseCreateDateCourseVO;
import beyond.momentours.date_course.command.domain.vo.response.ResponseDateCourseDetailVO;
import beyond.momentours.date_course.command.domain.vo.response.ResponseDateCourseListVO;
import beyond.momentours.date_course.command.domain.vo.response.ResponseUpdateDateCourseVO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DateCourseConverter {
    public DateCourseDTO fromCreateVOToDTO(RequestCreateDateCourseVO request) {
        return DateCourseDTO.builder()
                .courseTitle(request.getCourseTitle())
                .courseType(request.getCourseType())
                .courseMemo(request.getCourseMemo())
                .courseDisclosure(request.getCourseDisclosure())
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

    public DateCourseDTO fromUpdateVOToDTO(RequestUpdateDateCourseVO request, Long courseId) {
        return DateCourseDTO.builder()
                .courseId(courseId)
                .courseTitle(request.getCourseTitle())
                .courseType(request.getCourseType())
                .courseMemo(request.getCourseMemo())
                .courseDisclosure(request.getCourseDisclosure())
                .courseStartDate(request.getCourseStartDate())
                .courseEndDate(request.getCourseEndDate())
                .build();
    }

    public ResponseUpdateDateCourseVO fromDTOToUpdateVO(DateCourseDTO updatedCourseDTO) {
        return ResponseUpdateDateCourseVO.builder()
                .courseId(updatedCourseDTO.getCourseId())
                .courseTitle(updatedCourseDTO.getCourseTitle())
                .courseType(updatedCourseDTO.getCourseType())
                .courseMemo(updatedCourseDTO.getCourseMemo())
                .courseDisclosure(updatedCourseDTO.getCourseDisclosure())
                .courseStartDate(updatedCourseDTO.getCourseStartDate())
                .courseEndDate(updatedCourseDTO.getCourseEndDate())
                .createdAt(updatedCourseDTO.getCreatedAt())
                .updatedAt(updatedCourseDTO.getUpdatedAt())
                .memberId(updatedCourseDTO.getMemberId())
                .build();
    }

    public List<ResponseDateCourseListVO> fromDTOToListVO(List<DateCourseDTO> courses) {
        return courses.stream()
                .map(course -> ResponseDateCourseListVO.builder()
                        .courseId(course.getCourseId())
                        .courseTitle(course.getCourseTitle())
                        .courseType(course.getCourseType())
                        .courseMemo(course.getCourseMemo())
                        .courseLike(course.getCourseLike())
                        .courseView(course.getCourseView())
                        .courseStartDate(course.getCourseStartDate())
                        .courseEndDate(course.getCourseEndDate())
                        .memberId(course.getMemberId())
                        .build())
                .collect(Collectors.toList());
    }

    public ResponseDateCourseDetailVO fromDTOToDetailVO(DateCourseDTO dateCourseDTO) {
        return ResponseDateCourseDetailVO.builder()
                .courseId(dateCourseDTO.getCourseId())
                .courseTitle(dateCourseDTO.getCourseTitle())
                .courseType(dateCourseDTO.getCourseType())
                .courseMemo(dateCourseDTO.getCourseMemo())
                .courseLike(dateCourseDTO.getCourseLike())
                .courseView(dateCourseDTO.getCourseView())
                .courseStatus(dateCourseDTO.getCourseStatus())
                .courseStartDate(dateCourseDTO.getCourseStartDate())
                .courseEndDate(dateCourseDTO.getCourseEndDate())
                .createdAt(dateCourseDTO.getCreatedAt())
                .updatedAt(dateCourseDTO.getUpdatedAt())
                .memberId(dateCourseDTO.getMemberId())
                .build();
    }
}
