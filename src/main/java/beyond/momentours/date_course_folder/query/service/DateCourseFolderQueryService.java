package beyond.momentours.date_course_folder.query.service;

import beyond.momentours.date_course_folder.command.application.dto.DateCourseFolderDTO;
import beyond.momentours.member.command.application.dto.CustomUserDetails;

import java.util.List;

public interface DateCourseFolderQueryService {
    List<DateCourseFolderDTO> getMyDateCourseFolders(CustomUserDetails user);
}
