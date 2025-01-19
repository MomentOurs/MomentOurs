package beyond.momentours.reply.command.application.controller;

import beyond.momentours.common.exception.CommonException;
import beyond.momentours.reply.command.application.dto.ReplyDTO;
import beyond.momentours.reply.command.application.mapper.ReplyConvertor;
import beyond.momentours.reply.command.application.service.ReplyService;
import beyond.momentours.reply.command.domain.vo.request.RequestCreateReplyVO;
import beyond.momentours.reply.command.domain.vo.request.RequestUpdateReplyVO;
import beyond.momentours.reply.command.domain.vo.response.ResponseCreateReplyVO;
import beyond.momentours.reply.command.domain.vo.response.ResponseUpdateReplyVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("commandReplyController")
@RequestMapping("api/reply")
@Slf4j
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;
    private final ReplyConvertor replyConvertor;

    @PostMapping
    public ResponseEntity<?> createReply(@RequestBody RequestCreateReplyVO request) {
        log.info("등록 요청된 대댓글 데이터 : {}", request);
        try {
            ReplyDTO replyDTO = replyConvertor.fromCreateVOToDTO(request);
            ReplyDTO saveReplyDTO = replyService.createReply(replyDTO);
            ResponseCreateReplyVO response = replyConvertor.fromDTOToCreateVO(saveReplyDTO);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (CommonException e) {
            log.error("대댓글 등록 오류: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            log.error("예상치 못한 오류", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("예상치 못한 오류가 발생했습니다");
        }
    }

    @PatchMapping("/{replyId}")
    public ResponseEntity<?> updateReply(@PathVariable Long replyId, @RequestBody RequestUpdateReplyVO request) {
        log.info("수정 요청된 replyId: {}, 데이터: {}", replyId, request);
        try {
            ReplyDTO replyDTO = replyConvertor.fromUpdateVOToDTO(request);
            replyDTO.setReplyId(replyId);

            ReplyDTO updatedReply = replyService.updateReply(replyDTO);

            ResponseUpdateReplyVO response = replyConvertor.fromDTOToUpdateVO(updatedReply);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (CommonException e) {
            log.error("대댓글 수정 오류: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            log.error("예상치 못한 오류", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("예상치 못한 오류가 발생했습니다");
        }
    }

    @PatchMapping("/deactivate/{replyId}")
    public ResponseEntity<?> deleteReply(@PathVariable Long replyId) {
        log.info("삭제 요청된 대댓글 ID : {}", replyId);
        try {
            ReplyDTO deletedReply = replyService.deleteReply(replyId);
            log.info("삭제된 replyId : {}, 해당 대댓글의 상태 : {}", deletedReply.getReplyId(), deletedReply.getReplyStatus());
            return ResponseEntity.status(HttpStatus.OK).body("대댓글이 성공적으로 삭제되었습니다.");
        } catch (CommonException e) {
            log.error("대댓글 삭제 오류: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            log.error("예상치 못한 오류", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("예상치 못한 오류가 발생했습니다");
        }
    }
}
