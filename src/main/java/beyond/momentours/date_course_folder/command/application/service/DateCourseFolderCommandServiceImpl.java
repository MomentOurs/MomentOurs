package beyond.momentours.date_course_folder.command.application.service;

import beyond.momentours.common.exception.CommonException;
import beyond.momentours.common.exception.ErrorCode;
import beyond.momentours.date_course_folder.command.application.dto.DateCourseFolderDTO;
import beyond.momentours.date_course_folder.command.application.mapper.DateCourseFolderConverter;
import beyond.momentours.date_course_folder.command.domain.aggregate.entity.DateCourseFolder;
import beyond.momentours.date_course_folder.command.domain.repository.DateCourseFolderRepository;
import beyond.momentours.member.command.application.dto.CustomUserDetails;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DateCourseFolderCommandServiceImpl implements DateCourseFolderCommandService {

    private final DateCourseFolderRepository dateCourseFolderRepository;
    private final DateCourseFolderConverter dateCourseFolderConverter;

    @Transactional
    @Override
    public DateCourseFolderDTO createDateCourseFolder(DateCourseFolderDTO folderDTO) {
        DateCourseFolder folder = dateCourseFolderConverter.fromDTOToEntity(folderDTO);
        log.info("저장할 데이트 코스 폴더: {}", folder);
        DateCourseFolder savedFolder = dateCourseFolderRepository.save(folder);
        log.info("데이트 코스 폴더 등록 완료: {}", savedFolder);
        return dateCourseFolderConverter.fromEntityToDTO(savedFolder);
    }

    @Transactional
    @Override
    public DateCourseFolderDTO updateDateCourseFolder(DateCourseFolderDTO folderDTO, CustomUserDetails user) {
        DateCourseFolder folder = dateCourseFolderRepository.findById(folderDTO.getFolderId()).orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_FOLDER));
        if (!folder.getMemberId().equals(user.getMemberId())) throw new CommonException(ErrorCode.UNAUTHORIZED_ACCESS);

        folder = DateCourseFolder.builder().folderName(folderDTO.getFolderName()).build();
        DateCourseFolder updatedFolder = dateCourseFolderRepository.save(folder);

        log.info("데이트 코스 폴더 수정 완료: {}", updatedFolder);
        return dateCourseFolderConverter.fromEntityToDTO(updatedFolder);
    }

    @Transactional
    @Override
    public void deleteDateCourseFolder(Long folderId, CustomUserDetails user) {
        DateCourseFolder folder = dateCourseFolderRepository.findById(folderId).orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_FOLDER));
        if (!folder.getMemberId().equals(user.getMemberId())) throw new CommonException(ErrorCode.UNAUTHORIZED_ACCESS);

        dateCourseFolderRepository.delete(folder);
        log.info("데이트 코스 폴더 삭제 완료: folderId={}, userId={}", folderId, user.getMemberId());
    }
}
