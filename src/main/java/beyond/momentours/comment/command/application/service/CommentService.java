package beyond.momentours.comment.command.application.service;

import beyond.momentours.comment.command.application.dto.CommentDTO;
import beyond.momentours.member.command.application.dto.CustomUserDetails;

public interface CommentService {
    CommentDTO createComment(CommentDTO commentDTO, CustomUserDetails user);

    CommentDTO updateComment(CommentDTO commentDTO, CustomUserDetails user);

    CommentDTO deleteComment(Long commentId, CustomUserDetails user);
}
