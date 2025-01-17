package beyond.momentours.comment.command.application.mapper;

import beyond.momentours.comment.command.application.dto.CommentDTO;
import beyond.momentours.comment.command.domain.aggregate.entity.Comment;
import beyond.momentours.comment.command.domain.vo.request.RequestCreateCommentVO;
import beyond.momentours.comment.command.domain.vo.request.RequestUpdateCommentVO;
import beyond.momentours.comment.command.domain.vo.response.ResponseCreateCommentVO;
import beyond.momentours.comment.command.domain.vo.response.ResponseUpdateCommentVO;
import org.springframework.stereotype.Component;

@Component
public class CommentConverter {
    public CommentDTO fromCreateVOToDTO(RequestCreateCommentVO request) {
        return CommentDTO.builder()
                .commentContent(request.getCommentContent())
                .commentType(request.getCommentType())
                .quesId(request.getQuesId())
                .coupleLogId(request.getCoupleLogId())
                .build();
    }

    public ResponseCreateCommentVO fromDTOToCreateVO(CommentDTO saveCommentDTO) {
        return ResponseCreateCommentVO.builder()
                .commentContent(saveCommentDTO.getCommentContent())
                .commentType(saveCommentDTO.getCommentType())
                .quesId(saveCommentDTO.getQuesId())
                .coupleLogId(saveCommentDTO.getCoupleLogId())
                .build();
    }

    public Comment fromDTOToEntity(CommentDTO commentDTO) {
        return Comment.builder()
                .commentContent(commentDTO.getCommentContent())
                .commentType(commentDTO.getCommentType())
                .memberId(commentDTO.getMemberId())
                .quesId(commentDTO.getQuesId())
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
                .quesId(savedComment.getQuesId())
                .coupleLogId(savedComment.getCoupleLogId())
                .build();
    }

    public CommentDTO fromUpdateVOToDTO(RequestUpdateCommentVO vo) {
        return CommentDTO.builder()
                .commentContent(vo.getCommentContent())
                .build();
    }

    public ResponseUpdateCommentVO fromDTOToUpdateVO(CommentDTO dto) {
        return ResponseUpdateCommentVO.builder()
                .commentId(dto.getCommentId())
                .commentContent(dto.getCommentContent())
                .updatedAt(dto.getUpdatedAt())
                .build();
    }
}
