package beyond.momentours.reply.command.application.mapper;

import beyond.momentours.reply.command.application.dto.ReplyDTO;
import beyond.momentours.reply.command.domain.aggregate.entity.Reply;
import beyond.momentours.reply.command.domain.vo.request.RequestCreateReplyVO;
import beyond.momentours.reply.command.domain.vo.response.ResponseCreateReplyVO;
import org.springframework.stereotype.Component;

@Component
public class ReplyConvertor {
    public ReplyDTO fromCreateVOToDTO(RequestCreateReplyVO request) {
        return ReplyDTO.builder()
                .replyContent(request.getReplyContent())
                .commentId(request.getCommentId())
                .build();
    }

    public ResponseCreateReplyVO fromDTOToCreateVO(ReplyDTO saveReplyDTO) {
        return ResponseCreateReplyVO.builder()
                .replyContent(saveReplyDTO.getReplyContent())
                .commentId(saveReplyDTO.getCommentId())
                .build();
    }

    public Reply fromDTOToEntity(ReplyDTO replyDTO) {
        return Reply.builder()
                .replyContent(replyDTO.getReplyContent())
                .commentId(replyDTO.getCommentId())
                .memberId(replyDTO.getMemberId())
                .build();
    }

    public ReplyDTO fromEntityToDTO(Reply savedReply) {
        return ReplyDTO.builder()
                .replyId(savedReply.getReplyId())
                .replyContent(savedReply.getReplyContent())
                .replyStatus(savedReply.getReplyStatus())
                .createdAt(savedReply.getCreatedAt())
                .updatedAt(savedReply.getUpdatedAt())
                .commentId(savedReply.getCommentId())
                .memberId(savedReply.getMemberId())
                .build();
    }
}
