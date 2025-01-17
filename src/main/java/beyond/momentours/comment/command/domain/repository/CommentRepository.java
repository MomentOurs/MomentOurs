package beyond.momentours.comment.command.domain.repository;

import beyond.momentours.comment.command.domain.aggregate.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
