package elice.wanted.repository;

import elice.wanted.entity.Board;
import elice.wanted.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    public Page<Post> findAllPostByBoardOrderByCreatedDateDesc(Board board, Pageable pageable);

    public List<Post> findByBoard(Board board);

//    @Query("select p from Post p where p.board:=board and p.member.name like '%:=title%'")
//    public List<Post> findByBoardAndTitle(@Param("board") Board board, @Param("title") String title);

}
