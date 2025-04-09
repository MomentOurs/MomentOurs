package beyond.momentours.course_scrap_folder.command.application.service;

import beyond.momentours.course_scrap_folder.command.application.dto.CourseScrapFolderDTO;
import beyond.momentours.course_scrap_folder.command.domain.vo.FolderWithCourseIdsVO;
import beyond.momentours.course_scrap_folder.command.domain.vo.response.ResponseCourseScrapFolderVO;
import beyond.momentours.member.command.application.dto.CustomUserDetails;

import java.util.List;

public interface CourseScrapFolderService {
    CourseScrapFolderDTO createFolder(CourseScrapFolderDTO folderDTO, CustomUserDetails user);

    void deleteFolder(Long folderId, CustomUserDetails user);

    List<ResponseCourseScrapFolderVO> getFoldersByMemberId(Long memberId);

    List<FolderWithCourseIdsVO> getFoldersWithCourses(Long memberId);
}
