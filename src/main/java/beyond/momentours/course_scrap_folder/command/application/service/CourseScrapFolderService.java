package beyond.momentours.course_scrap_folder.command.application.service;

import beyond.momentours.course_scrap_folder.command.application.dto.CourseScrapFolderDTO;
import beyond.momentours.member.command.application.dto.CustomUserDetails;

public interface CourseScrapFolderService {
    CourseScrapFolderDTO createFolder(CourseScrapFolderDTO folderDTO, CustomUserDetails user);

    void deleteFolder(Long folderId, CustomUserDetails user);
}
