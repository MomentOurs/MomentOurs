package beyond.momentours.course_scrap_folder.command.application.service;

import beyond.momentours.common.exception.CommonException;
import beyond.momentours.common.exception.ErrorCode;
import beyond.momentours.course_scrap.command.domain.aggregate.entity.CourseScrap;
import beyond.momentours.course_scrap.query.repository.CourseScrapMapper;
import beyond.momentours.course_scrap_folder.command.application.dto.CourseScrapFolderDTO;
import beyond.momentours.course_scrap_folder.command.application.mapper.CourseScrapFolderConverter;
import beyond.momentours.course_scrap_folder.command.domain.aggregate.entity.CourseScrapFolder;
import beyond.momentours.course_scrap_folder.command.domain.repository.CourseScrapFolderRepository;
import beyond.momentours.course_scrap_folder.command.domain.vo.CourseScrapCountVO;
import beyond.momentours.course_scrap_folder.command.domain.vo.FolderWithCourseIdsVO;
import beyond.momentours.course_scrap_folder.command.domain.vo.response.ResponseCourseScrapFolderVO;
import beyond.momentours.course_scrap_folder.query.repository.CourseScrapFolderMapper;
import beyond.momentours.member.command.application.dto.CustomUserDetails;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service("commandDateCourseScrapFolderService")
@RequiredArgsConstructor
public class CourseScrapFolderServiceImpl implements CourseScrapFolderService {

    private final CourseScrapFolderRepository courseScrapFolderRepository;
    private final CourseScrapFolderConverter courseScrapFolderConverter;
    private final CourseScrapFolderMapper courseScrapFolderDAO;
    private final CourseScrapMapper courseScrapDAO;

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

    @Transactional
    public List<ResponseCourseScrapFolderVO> getFoldersByMemberId(Long memberId) {
        List<CourseScrapFolder> folders = courseScrapFolderDAO.findByMemberId(memberId);
        List<Long> folderIds = folders.stream()
                .map(CourseScrapFolder::getCourseScrapFolderId)
                .toList();

        List<CourseScrapCountVO> counts = courseScrapFolderDAO.countCoursesByFolderIds(folderIds);
        Map<Long, Integer> folderIdToCount = counts.stream()
                .collect(Collectors.toMap(CourseScrapCountVO::getFolderId, CourseScrapCountVO::getCourseCount));

        return folders.stream()
                .map(folder -> {
                    int count = folderIdToCount.getOrDefault(folder.getCourseScrapFolderId(), 0);
                    return courseScrapFolderConverter.fromEntityToResponseVO(folder, count);
                })
                .toList();
    }


    @Override
    public List<FolderWithCourseIdsVO> getFoldersWithCourses(Long memberId) {
        List<CourseScrapFolder> folders = courseScrapFolderDAO.findByMemberId(memberId);

        return folders.stream()
                .map(folder -> {
                    List<Long> courseIds = courseScrapDAO.findByCourseScrapFolderId(folder.getCourseScrapFolderId())
                            .stream()
                            .map(CourseScrap::getCourseId)
                            .collect(Collectors.toList());

                    return FolderWithCourseIdsVO.builder()
                            .courseScrapFolderId(folder.getCourseScrapFolderId())
                            .folderName(folder.getFolderName())
                            .folderDescription(folder.getFolderDescription())
                            .folderImageUrl(folder.getFolderImage())
                            .courseIds(courseIds)
                            .build();
                })
                .collect(Collectors.toList());
    }
}
