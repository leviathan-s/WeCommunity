package elice.wanted.service;

import elice.wanted.dto.PostUpdateDto;
import elice.wanted.dto.PostWriteDto;
import elice.wanted.entity.*;
import elice.wanted.exception.NoSuchPostException;
import elice.wanted.repository.MemberRepository;
import elice.wanted.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final CategoryService categoryService;
    private final BoardService boardService;


    public Post save(PostWriteDto postWriteDto) {

        Board board = boardService.findById(postWriteDto.getBoardId());

        Post post = new Post();
        post.setBoard(board);
        post.setTitle(postWriteDto.getTitle());
        post.set_notice(postWriteDto.getIsNotice());
        post.setContent(postWriteDto.getContent());

        Member member = memberRepository.findById(postWriteDto.getMemberSessionInfo().getId()).get();
        post.setMember(member);

        for (String category : postWriteDto.getPostCategories()) {
            Category categoryObject = categoryService.findByName(category);
            PostCategory postCategory = PostCategory.createPostCategory(categoryObject);
            postCategory.setPost(post);
            post.getPostCategories().add(postCategory);
        }

        Post savedPost = postRepository.save(post);
        board.getPosts().add(savedPost);
        savedPost.setViews(0L);

        return savedPost;
    }

    public Post edit(Long postId, PostUpdateDto postUpdateDto) {

        Post post = findById(postId);

        post.setTitle(postUpdateDto.getTitle());
        post.set_notice(postUpdateDto.getNotice());
        post.setContent(postUpdateDto.getContent());
        post.getPostCategories().clear();

        for (String category : postUpdateDto.getPostCategories()) {
            Category categoryObject = categoryService.findByName(category);
            PostCategory postCategory = PostCategory.createPostCategory(categoryObject);
            postCategory.setPost(post);
            post.getPostCategories().add(postCategory);
        }

        return post;
    }



    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public Post findById(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new NoSuchPostException("해당 포스트가 존재하지 않습니다."));
    }

    public Post findByIdThroughView(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new NoSuchPostException("해당 포스트가 존재하지 않습니다."));
        post.viewPost();
        return post;
    }

    public void delete(Post post) {
        postRepository.delete(post);
    }

    public List<Post> findByBoard(Board board) {
        return postRepository.findByBoard(board);
    }

    public Page<Post> findByBoardPaging(Board board, Pageable pageable) {
        return postRepository.findAllPostByBoardOrderByCreatedDateDesc(board, pageable);
    }
}
