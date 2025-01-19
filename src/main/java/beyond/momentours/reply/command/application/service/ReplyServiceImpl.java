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

    private void validateCommentId(Long commentId) {
        Comment comment = commentDAO.findCommentById(commentId);
        if (comment == null || !comment.getCommentStatus()) {
            throw new CommonException(ErrorCode.NOT_FOUND_COMMENT);
        }
    }
}
