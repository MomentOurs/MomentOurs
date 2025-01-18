package beyond.momentours.comment.command.application.service;

import beyond.momentours.comment.command.application.dto.CommentDTO;

public interface CommentService {
    CommentDTO createComment(CommentDTO commentDTO);

    CommentDTO updateComment(CommentDTO commentDTO);
}
