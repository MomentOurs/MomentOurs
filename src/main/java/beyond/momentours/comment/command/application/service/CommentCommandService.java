package beyond.momentours.comment.command.application.service;

import beyond.momentours.comment.command.application.dto.CommentDTO;
import beyond.momentours.member.command.application.dto.CustomUserDetails;
import jakarta.transaction.Transactional;

public interface CommentCommandService {
    @Transactional
    CommentDTO createComment(CommentDTO commentDTO, CustomUserDetails user);

    @Transactional
    CommentDTO updateComment(CommentDTO commentDTO, CustomUserDetails user);

    @Transactional
    CommentDTO deleteComment(Long commentId, CustomUserDetails user);
}
