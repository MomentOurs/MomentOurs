package beyond.momentours.course_scrap_folder.command.application.service;

import beyond.momentours.common.exception.CommonException;
import beyond.momentours.common.exception.ErrorCode;
import beyond.momentours.course_scrap_folder.command.application.dto.CourseScrapFolderDTO;
import beyond.momentours.course_scrap_folder.command.application.mapper.CourseScrapFolderConverter;
import beyond.momentours.course_scrap_folder.command.domain.aggregate.entity.CourseScrapFolder;
import beyond.momentours.course_scrap_folder.command.domain.repository.CourseScrapFolderRepository;
import beyond.momentours.member.command.application.dto.CustomUserDetails;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service("commandDateCourseScrapFolderService")
@RequiredArgsConstructor
public class CourseScrapFolderServiceImpl implements CourseScrapFolderService {

    private final CourseScrapFolderRepository courseScrapFolderRepository;
    private final CourseScrapFolderConverter courseScrapFolderConverter;

    @Override
    @Transactional
    public CourseScrapFolderDTO createFolder(CourseScrapFolderDTO folderDTO, CustomUserDetails user) {
        try {
            Long memberId = user.getMemberId();
            folderDTO.setMemberId(memberId);

            CourseScrapFolder folderEntity = courseScrapFolderConverter.fromDTOToEntity(folderDTO);
            log.info("저장할 폴더 데이터: {}", folderEntity);
            CourseScrapFolder savedFolder = courseScrapFolderRepository.save(folderEntity);
            log.info("폴더 저장 성공: {}", savedFolder);

            return courseScrapFolderConverter.fromEntityToDTO(savedFolder);
        } catch (Exception e) {
            log.error("폴더 저장 중 오류 발생", e);
            throw new CommonException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    public void deleteFolder(Long folderId, CustomUserDetails user) {
        CourseScrapFolder folder = courseScrapFolderRepository.findById(folderId).orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_DATE_COURSE_SCRAP_FOLDER));

        if (!folder.getMemberId().equals(user.getMemberId())) throw new CommonException(ErrorCode.UNAUTHORIZED_ACCESS);

        courseScrapFolderRepository.deleteById(folderId);
        log.info("폴더 삭제 완료: folderId={}", folderId);
    }
}
