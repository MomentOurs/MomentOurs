package beyond.momentours.reply.command.application.service;

import beyond.momentours.comment.command.domain.aggregate.entity.Comment;
import beyond.momentours.comment.query.repository.CommentMapper;
import beyond.momentours.common.exception.CommonException;
import beyond.momentours.common.exception.ErrorCode;
import beyond.momentours.reply.command.application.dto.ReplyDTO;
import beyond.momentours.reply.command.application.mapper.ReplyConvertor;
import beyond.momentours.reply.command.domain.aggregate.entity.Reply;
import beyond.momentours.reply.command.domain.repository.ReplyRepository;
import beyond.momentours.reply.query.repository.ReplyMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service("commandReplyService")
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {

    private final ReplyRepository replyRepository;
    private final ReplyConvertor replyConvertor;
    private final CommentMapper commentDAO;
    private final ReplyMapper replyDAO;

    @Override
    public ReplyDTO createReply(ReplyDTO replyDTO) {
        validateCommentId(replyDTO.getCommentId());

//        Long memberId = getLoggedInMemberId(); // 로그인한 사용자의 ID 가져오기
        Long memberId = 0L; // (임시로 0L 사용)
        replyDTO.setMemberId(memberId);

        Reply reply = replyConvertor.fromDTOToEntity(replyDTO);
        log.info("저장할 대댓글 데이터: {}", reply);

        reply.create(reply);
        Reply savedReply = replyRepository.save(reply);

        return replyConvertor.fromEntityToDTO(savedReply);
    }

    @Override
    public ReplyDTO updateReply(ReplyDTO replyDTO) {
        validateCommentId(replyDTO.getCommentId());

//        Long memberId = getLoggedInMemberId(); // 로그인한 사용자의 ID 가져오기
        Long memberId = 0L;

        Reply existingReply = replyRepository.findById(replyDTO.getReplyId()).orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_REPLY));
        log.info("기존 Reply 데이터: {}", existingReply);

        if (!existingReply.getReplyStatus()) {
            log.error("비활성화된 대댓글: replyId: {}", replyDTO.getReplyId());
            throw new CommonException(ErrorCode.INACTIVE_REPLY);
        }

        if (!existingReply.getMemberId().equals(memberId)) {
            log.error("수정 권한 없음: 요청한 사용자 ID: {}, 대댓글 작성자 ID: {}", memberId, existingReply.getMemberId());
            throw new CommonException(ErrorCode.ACCESS_DENIED);
        }

        updateReplyContent(replyDTO, existingReply);
        log.info("수정 후 Reply 데이터: {}", existingReply);

        replyRepository.save(existingReply);

        return replyConvertor.fromEntityToDTO(existingReply);
    }


    @Override
    public ReplyDTO deleteReply(Long replyId) {
        Reply existingReply = replyRepository.findById(replyId).orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_REPLY));
        log.info("삭제 요청된 Reply 데이터: {}", existingReply);

        validateCommentId(existingReply.getCommentId());

//        Long memberId = getLoggedInMemberId(); // 로그인한 사용자의 ID 가져오기
        Long memberId = 0L;

        if (!existingReply.getMemberId().equals(memberId)) {
            log.error("삭제 권한 없음: 요청한 사용자 ID: {}, 대댓글 작성자 ID: {}", memberId, existingReply.getMemberId());
            throw new CommonException(ErrorCode.ACCESS_DENIED);
        }

        existingReply.updateStatus(false);
        log.info("상태 변경 후 Reply 데이터: {}", existingReply);

        replyRepository.save(existingReply);

        return replyConvertor.fromEntityToDTO(existingReply);
    }

    private void validateCommentId(Long commentId) {
        Comment comment = commentDAO.findCommentById(commentId);
        if (comment == null || !comment.getCommentStatus()) {
            throw new CommonException(ErrorCode.NOT_FOUND_COMMENT);
        }
    }

    private void updateReplyContent(ReplyDTO replyDTO, Reply existingReply) {
        if (replyDTO.getReplyContent() != null) {
            existingReply.updateContent(replyDTO.getReplyContent());
        }
    }
}
