package beyond.momentours.comment.query.repository;

import beyond.momentours.comment.command.domain.aggregate.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CommentMapper {
    Comment findCommentById(@Param("commentId") Long commentId);
}
