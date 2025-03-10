package beyond.momentours.date_course_folder.command.application.service;

import beyond.momentours.date_course_folder.command.application.dto.DateCourseFolderDTO;
import beyond.momentours.member.command.application.dto.CustomUserDetails;

public interface DateCourseFolderCommandService {
    DateCourseFolderDTO createDateCourseFolder(DateCourseFolderDTO folderDTO);
    DateCourseFolderDTO updateDateCourseFolder(DateCourseFolderDTO folderDTO, CustomUserDetails user);
    void deleteDateCourseFolder(Long folderId, CustomUserDetails user);
}
