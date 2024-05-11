package elice.wanted.repository;

import elice.wanted.entity.Comment;
import elice.wanted.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    public List<Comment> findByPost(Post post);
}
