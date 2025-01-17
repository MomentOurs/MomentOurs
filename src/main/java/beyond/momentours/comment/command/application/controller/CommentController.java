package beyond.momentours.comment.command.application.controller;

import beyond.momentours.comment.command.application.dto.CommentDTO;
import beyond.momentours.comment.command.application.mapper.CommentConverter;
import beyond.momentours.comment.command.application.service.CommentService;
import beyond.momentours.comment.command.domain.vo.request.RequestCreateCommentVO;
import beyond.momentours.comment.command.domain.vo.response.ResponseCreateCommentVO;
import beyond.momentours.common.exception.CommonException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("commandCommentController")
@RequestMapping("api/comment")
@Slf4j
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final CommentConverter commentConverter;

    @PostMapping
    public ResponseEntity<?> createComment(@RequestBody RequestCreateCommentVO request) {
        log.info("등록 요청된 댓글 데이터 : {}", request);
        try {
            CommentDTO commentDTO = commentConverter.fromCreateVOToDTO(request);
            CommentDTO saveCommentDTO = commentService.createComment(commentDTO);
            ResponseCreateCommentVO response = commentConverter.fromDTOToCreateVO(saveCommentDTO);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (CommonException e) {
            log.error("댓글 등록 오류: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            log.error("예상치 못한 오류", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("예상치 못한 오류가 발생했습니다");
        }
    }
}
