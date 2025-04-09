package beyond.momentours.course_scrap.command.application.service;

import beyond.momentours.course_scrap.command.application.dto.CourseScrapDTO;
import beyond.momentours.course_scrap.command.domain.vo.CourseScrapVO;
import beyond.momentours.member.command.application.dto.CustomUserDetails;
import jakarta.transaction.Transactional;

import java.util.List;

public interface CourseScrapService {
    @Transactional
    CourseScrapDTO createCourseScrap(CourseScrapDTO scrapDTO, CustomUserDetails user);

    @Transactional
    void deleteCourseScrap(Long scrapId, CustomUserDetails user);

    @Transactional
    List<CourseScrapVO> getCourseScrapsByFolderId(Long folderId, CustomUserDetails user);
}
