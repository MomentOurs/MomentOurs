package beyond.momentours.comment.query.service;

import beyond.momentours.comment.command.application.dto.CommentDTO;

import java.util.List;

public interface CommentQueryService {
    List<CommentDTO> getCommentsByCoupleLogId(Long coupleLogId);

    List<CommentDTO> getCommentsByQuestionId(Long userQuesId);
}
