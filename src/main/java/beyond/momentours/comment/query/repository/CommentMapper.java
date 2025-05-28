package beyond.momentours.comment.query.repository;

import beyond.momentours.comment.command.domain.aggregate.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentMapper {
    List<Comment> findCommentsByMomentId(@Param("momentId") Long momentId);

    List<Comment> findCommentsByQuestionId(@Param("userQuesId") Long userQuesId);
}
