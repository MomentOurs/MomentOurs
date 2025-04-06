package beyond.momentours.date_course.command.application.mapper;

import beyond.momentours.date_course.command.application.dto.DateCourseDTO;
import beyond.momentours.date_course.command.domain.aggregate.entity.DateCourse;
import beyond.momentours.date_course.command.domain.vo.request.RequestCreateDateCourseVO;
import beyond.momentours.date_course.command.domain.vo.request.RequestUpdateDateCourseScheduleVO;
import beyond.momentours.date_course.command.domain.vo.request.RequestUpdateDateCourseVO;
import beyond.momentours.date_course.command.domain.vo.response.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DateCourseConverter {
    public DateCourseDTO fromCreateVOToDTO(RequestCreateDateCourseVO request) {
        return DateCourseDTO.builder()
                .courseTitle(request.getCourseTitle())
                .courseType(request.getCourseType())
                .courseDisclosure(request.getCourseDisclosure())
                .folderId(request.getFolderId())
                .courseStartDate(request.getCourseStartDate())
                .courseEndDate(request.getCourseEndDate())
                .courseCertification(false)
                .build();
    }

    public ResponseCreateDateCourseVO fromDTOToCreateVO(DateCourseDTO saveDateCourseDTO) {
        return ResponseCreateDateCourseVO.builder()
                .courseId(saveDateCourseDTO.getCourseId())
                .courseTitle(saveDateCourseDTO.getCourseTitle())
                .courseType(saveDateCourseDTO.getCourseType())
                .courseDisclosure(saveDateCourseDTO.getCourseDisclosure())
                .courseLike(saveDateCourseDTO.getCourseLike())
                .courseView(saveDateCourseDTO.getCourseView())
                .courseStatus(saveDateCourseDTO.getCourseStatus())
                .courseStartDate(saveDateCourseDTO.getCourseStartDate())
                .courseEndDate(saveDateCourseDTO.getCourseEndDate())
                .courseCertification(saveDateCourseDTO.getCourseCertification())
                .createdAt(saveDateCourseDTO.getCreatedAt())
                .updatedAt(saveDateCourseDTO.getUpdatedAt())
                .memberId(saveDateCourseDTO.getMemberId())
                .folderId(saveDateCourseDTO.getFolderId())
                .build();
    }

    public DateCourse fromDTOToEntity(DateCourseDTO dateCourseDTO) {
        return DateCourse.builder()
                .courseTitle(dateCourseDTO.getCourseTitle())
                .courseType(dateCourseDTO.getCourseType())
                .courseDisclosure(dateCourseDTO.getCourseDisclosure())
                .courseStartDate(dateCourseDTO.getCourseStartDate())
                .courseEndDate(dateCourseDTO.getCourseEndDate())
                .memberId(dateCourseDTO.getMemberId())
                .folderId(dateCourseDTO.getFolderId())
                .build();
    }

    public DateCourseDTO fromEntityToDTO(DateCourse savedCourse) {
        return DateCourseDTO.builder()
                .courseId(savedCourse.getCourseId())
                .courseTitle(savedCourse.getCourseTitle())
                .courseType(savedCourse.getCourseType())
                .courseDisclosure(savedCourse.getCourseDisclosure())
                .courseLike(savedCourse.getCourseLike())
                .courseView(savedCourse.getCourseView())
                .courseStatus(savedCourse.getCourseStatus())
                .courseStartDate(savedCourse.getCourseStartDate())
                .courseEndDate(savedCourse.getCourseEndDate())
                .createdAt(savedCourse.getCreatedAt())
                .updatedAt(savedCourse.getUpdatedAt())
                .memberId(savedCourse.getMemberId())
                .folderId(savedCourse.getFolderId())
                .build();
    }

    public DateCourseDTO fromUpdateVOToDTO(RequestUpdateDateCourseVO request, Long courseId) {
        return DateCourseDTO.builder()
                .courseId(courseId)
                .courseTitle(request.getCourseTitle())
                .courseType(request.getCourseType())
                .courseDisclosure(request.getCourseDisclosure())
                .build();
    }

    public ResponseUpdateDateCourseVO fromDTOToUpdateVO(DateCourseDTO updatedCourseDTO) {
        return ResponseUpdateDateCourseVO.builder()
                .courseId(updatedCourseDTO.getCourseId())
                .courseTitle(updatedCourseDTO.getCourseTitle())
                .courseType(updatedCourseDTO.getCourseType())
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
                        .courseLike(course.getCourseLike())
                        .courseView(course.getCourseView())
                        .courseStartDate(course.getCourseStartDate())
                        .courseEndDate(course.getCourseEndDate())
                        .memberId(course.getMemberId())
                        .build())
                .collect(Collectors.toList());
    }

    public ResponseDateCourseDetailWithLocationVO fromDTOToDetailVO(DateCourseDTO dateCourseDTO) {
        return ResponseDateCourseDetailWithLocationVO.builder()
                .courseId(dateCourseDTO.getCourseId())
                .courseTitle(dateCourseDTO.getCourseTitle())
                .courseType(dateCourseDTO.getCourseType())
                .courseLike(dateCourseDTO.getCourseLike())
                .courseView(dateCourseDTO.getCourseView())
                .courseStatus(dateCourseDTO.getCourseStatus())
                .courseStartDate(dateCourseDTO.getCourseStartDate())
                .courseEndDate(dateCourseDTO.getCourseEndDate())
                .createdAt(dateCourseDTO.getCreatedAt())
                .updatedAt(dateCourseDTO.getUpdatedAt())
                .memberId(dateCourseDTO.getMemberId())
                .locations(dateCourseDTO.getLocations())
                .build();
    }

    public DateCourseDTO fromUpdateScheduleVOToDTO(RequestUpdateDateCourseScheduleVO request, Long courseId) {
        return DateCourseDTO.builder()
                .courseId(courseId)
                .courseStartDate(request.getCourseStartDate())
                .courseEndDate(request.getCourseEndDate())
                .build();
    }
}
