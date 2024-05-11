package elice.wanted.service;

import elice.wanted.dto.CommentSaveDto;
import elice.wanted.entity.Comment;
import elice.wanted.entity.Member;
import elice.wanted.entity.Post;
import elice.wanted.repository.CommentRepository;
import elice.wanted.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostService postService;
    private final MemberRepository memberRepository;

    public Comment save(Long postId, CommentSaveDto commentSaveDto) {

        Post post = postService.findById(postId);
        Comment comment = new Comment();

        comment.setContent(commentSaveDto.getContent());
        comment.setPost(post);
        comment.setSecret(commentSaveDto.getSecret());

        Long memberId = commentSaveDto.getMemberSessionInfo().getId();
        Member member = memberRepository.findById(memberId).get();
        comment.setMember(member);
        Comment savedComment = commentRepository.save(comment);
        post.getComments().add(savedComment);

        return savedComment;
    }

    public List<Comment> findByPost(Post post) {
        return commentRepository.findByPost(post);
    }

    public void deleteById(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
