package beyond.momentours.comment.command.application.mapper;

import beyond.momentours.comment.command.application.dto.CommentDTO;
import beyond.momentours.comment.command.domain.aggregate.entity.Comment;
import beyond.momentours.comment.command.domain.vo.request.RequestCreateCommentVO;
import beyond.momentours.comment.command.domain.vo.response.ResponseCreateCommentVO;
import org.springframework.stereotype.Component;

@Component
public class CommentConverter {
    public CommentDTO fromCreateVOToDTO(RequestCreateCommentVO request) {
        return CommentDTO.builder()
                .commentContent(request.getCommentContent())
                .commentType(request.getCommentType())
                .momentId(request.getMomentId())
                .coupleLogId(request.getCoupleLogId())
                .build();
    }

    public ResponseCreateCommentVO fromDTOToCreateVO(CommentDTO saveCommentDTO) {
        return ResponseCreateCommentVO.builder()
                .commentContent(saveCommentDTO.getCommentContent())
                .commentType(saveCommentDTO.getCommentType())
                .momentId(saveCommentDTO.getMomentId())
                .coupleLogId(saveCommentDTO.getCoupleLogId())
                .build();
    }

    public Comment fromDTOToEntity(CommentDTO commentDTO) {
        return Comment.builder()
                .commentContent(commentDTO.getCommentContent())
                .commentType(commentDTO.getCommentType())
                .memberId(commentDTO.getMemberId())
                .momentId(commentDTO.getMomentId())
                .coupleLogId(commentDTO.getCoupleLogId())
                .build();
    }

    public CommentDTO fromEntityToDTO(Comment savedComment) {
        return CommentDTO.builder()
                .commentId(savedComment.getCommentId())
                .commentContent(savedComment.getCommentContent())
                .commentType(savedComment.getCommentType())
                .commentStatus(savedComment.getCommentStatus())
                .createdAt(savedComment.getCreatedAt())
                .updatedAt(savedComment.getUpdatedAt())
                .memberId(savedComment.getMemberId())
                .momentId(savedComment.getMomentId())
                .coupleLogId(savedComment.getCoupleLogId())
                .build();
    }
}
