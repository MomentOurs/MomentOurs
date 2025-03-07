package beyond.momentours.date_course_folder.query.service;

import beyond.momentours.date_course_folder.command.application.dto.DateCourseFolderDTO;
import beyond.momentours.date_course_folder.command.application.mapper.DateCourseFolderConverter;
import beyond.momentours.date_course_folder.command.domain.aggregate.entity.DateCourseFolder;
import beyond.momentours.date_course_folder.query.repository.DateCourseFolderMapper;
import beyond.momentours.member.command.application.dto.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DateCourseFolderQueryServiceImpl implements DateCourseFolderQueryService {

    private final DateCourseFolderMapper dateCourseFolderMapper;
    private final DateCourseFolderConverter dateCourseFolderConverter;

    @Override
    public List<DateCourseFolderDTO> getMyDateCourseFolders(CustomUserDetails user) {
        Long memberId = user.getMemberId();
        log.info("사용자 ID: {} 의 폴더 목록 조회", memberId);

        List<DateCourseFolder> folders = dateCourseFolderMapper.findFoldersByMemberId(memberId);
        return folders.stream()
                .map(dateCourseFolderConverter::fromEntityToDTO)
                .collect(Collectors.toList());
    }
}
