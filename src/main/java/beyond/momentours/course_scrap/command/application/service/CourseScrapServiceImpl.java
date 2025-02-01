package beyond.momentours.course_scrap.command.application.service;

import beyond.momentours.common.exception.CommonException;
import beyond.momentours.common.exception.ErrorCode;
import beyond.momentours.course_scrap.command.application.dto.CourseScrapDTO;
import beyond.momentours.course_scrap.command.application.mapper.CourseScrapConverter;
import beyond.momentours.course_scrap.command.domain.aggregate.entity.CourseScrap;
import beyond.momentours.course_scrap.command.domain.repository.CourseScrapRepository;
import beyond.momentours.course_scrap.command.domain.vo.CourseScrapVO;
import beyond.momentours.course_scrap.query.repository.CourseScrapMapper;
import beyond.momentours.member.command.application.dto.CustomUserDetails;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service("commandCourseScrapService")
@RequiredArgsConstructor
public class CourseScrapServiceImpl implements CourseScrapService {

    private final CourseScrapRepository courseScrapRepository;
    private final CourseScrapConverter courseScrapConverter;
    private final CourseScrapMapper courseScrapDAO;

    @Override
    @Transactional
    public CourseScrapDTO createCourseScrap(CourseScrapDTO scrapDTO, CustomUserDetails user) {
        try {
            CourseScrap scrapEntity = courseScrapConverter.fromDTOToEntity(scrapDTO);
            log.info("저장할 스크랩 데이터: {}", scrapEntity);
            CourseScrap savedScrap = courseScrapRepository.save(scrapEntity);
            log.info("스크랩 저장 성공: {}", savedScrap);

            return courseScrapConverter.fromEntityToDTO(savedScrap);
        } catch (Exception e) {
            log.error("스크랩 저장 중 오류 발생", e);
            throw new CommonException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    public void deleteCourseScrap(Long scrapId, CustomUserDetails user) {
        CourseScrap scrap = courseScrapRepository.findById(scrapId).orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_COURSE_SCRAP));
        Long folderOwnerId = courseScrapDAO.findFolderOwnerId(scrap.getCourseScrapFolderId());
        if (!folderOwnerId.equals(user.getMemberId())) throw new CommonException(ErrorCode.UNAUTHORIZED_ACCESS);

        courseScrapRepository.delete(scrap);
        log.info("데이트 코스 스크랩 삭제 완료: scrapId={}", scrapId);
    }


    @Override
    @Transactional
    public List<CourseScrapVO> getCourseScrapsByFolderId(Long folderId, CustomUserDetails user) {
        Long folderOwnerId = courseScrapDAO.findFolderOwnerId(folderId);
        if (!folderOwnerId.equals(user.getMemberId())) throw new CommonException(ErrorCode.UNAUTHORIZED_ACCESS);
        return courseScrapDAO.findByFolderId(folderId);
    }
}

