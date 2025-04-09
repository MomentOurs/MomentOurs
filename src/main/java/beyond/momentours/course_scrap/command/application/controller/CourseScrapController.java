package beyond.momentours.course_scrap.command.application.controller;

import beyond.momentours.common.exception.CommonException;
import beyond.momentours.course_scrap.command.application.dto.CourseScrapDTO;
import beyond.momentours.course_scrap.command.application.mapper.CourseScrapConverter;
import beyond.momentours.course_scrap.command.application.service.CourseScrapService;
import beyond.momentours.course_scrap.command.domain.vo.CourseScrapVO;
import beyond.momentours.course_scrap.command.domain.vo.request.RequestCreateCourseScrapVO;
import beyond.momentours.course_scrap.command.domain.vo.response.ResponseCreateCourseScrapVO;
import beyond.momentours.member.command.application.dto.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("commandCourseScrapController")
@RequestMapping("api/course-scrap")
@Slf4j
@RequiredArgsConstructor
public class CourseScrapController {

    private final CourseScrapService courseScrapService;
    private final CourseScrapConverter courseScrapConverter;

    @PostMapping
    public ResponseEntity<?> createCourseScrap(@RequestBody RequestCreateCourseScrapVO request, @AuthenticationPrincipal CustomUserDetails user) {
        log.info("데이트 코스 즐겨찾기 등록 요청: {}", request);
        try {
            CourseScrapDTO scrapDTO = courseScrapConverter.fromCreateVOToDTO(request);
            CourseScrapDTO savedScrapDTO = courseScrapService.createCourseScrap(scrapDTO, user);
            ResponseCreateCourseScrapVO response = courseScrapConverter.fromDTOToCreateVO(savedScrapDTO);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            log.error("데이트 코스 즐겨찾기 등록 실패", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("즐겨찾기 등록 중 오류가 발생했습니다.");
        }
    }

    @DeleteMapping("/{scrapId}")
    public ResponseEntity<?> deleteCourseScrap(@PathVariable Long scrapId, @AuthenticationPrincipal CustomUserDetails user) {
        log.info("데이트 코스 즐겨찾기 삭제 요청: scrapId={}, user={}", scrapId, user.getMemberId());
        try {
            courseScrapService.deleteCourseScrap(scrapId, user);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (CommonException e) {
            log.error("즐겨찾기 삭제 오류: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            log.error("즐겨찾기 삭제 중 예상치 못한 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("즐겨찾기 삭제 중 오류가 발생했습니다.");
        }
    }

    @GetMapping("/{folderId}")
    public ResponseEntity<?> getCourseScraps(@PathVariable Long folderId, @AuthenticationPrincipal CustomUserDetails user) {
        log.info("폴더 내 데이트 코스 조회 요청: folderId={}, userId={}", folderId, user.getMemberId());
        try {
            List<CourseScrapVO> scraps = courseScrapService.getCourseScrapsByFolderId(folderId, user);
            return ResponseEntity.status(HttpStatus.OK).body(scraps);
        } catch (Exception e) {
            log.error("데이트 코스 스크랩 조회 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("조회 중 오류가 발생했습니다.");
        }
    }
}
