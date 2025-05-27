package beyond.momentours.comment.query.controller;

import beyond.momentours.comment.command.application.dto.CommentDTO;
import beyond.momentours.comment.query.service.CommentQueryService;
import beyond.momentours.common.exception.CommonException;
import beyond.momentours.member.command.application.dto.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/comment")
@Slf4j
@RequiredArgsConstructor
public class CommentQueryController {

    private final CommentQueryService commentQueryService;

    @GetMapping("/moment/{momentId}")
    public ResponseEntity<?> getCommentsByMoment(@PathVariable Long momentId) {
        log.info("댓글 조회 요청 - 추억 ID: {}", momentId);
        try {
            List<CommentDTO> comments = commentQueryService.getCommentsByMomentId(momentId);
            return ResponseEntity.ok(comments);
        } catch (CommonException e) {
            log.error("추억 댓글 조회 오류: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            log.error("예상치 못한 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("예상치 못한 오류가 발생했습니다.");
        }
    }

    @GetMapping("/question/{userQuesId}")
    public ResponseEntity<?> getCommentsByQuestion(@PathVariable Long userQuesId, @AuthenticationPrincipal CustomUserDetails user) {
        log.info("조회 요청 - 랜덤 질문 ID: {}, 사용자 ID: {}", userQuesId, user.getMemberId());
        try {
            List<CommentDTO> comments = commentQueryService.getCommentsByQuestionId(userQuesId);
            return ResponseEntity.ok(comments);
        } catch (CommonException e) {
            log.error("랜덤 질문 댓글 조회 오류: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            log.error("예상치 못한 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("예상치 못한 오류가 발생했습니다.");
        }
    }
}

