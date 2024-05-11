package elice.wanted.initializer;

import elice.wanted.dto.BoardSaveDto;
import elice.wanted.entity.*;
import elice.wanted.repository.BoardRepository;
import elice.wanted.repository.CategoryRepository;
import elice.wanted.repository.PostRepository;
import elice.wanted.service.BoardService;
import elice.wanted.service.MemberService;
import elice.wanted.service.PostService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class InitializeDatabase {

    private final BoardRepository boardRepository;
    private final CategoryRepository categoryRepository;
    private final MemberService memberService;
    private final PostRepository postRepository;

    @Transactional
    @PostConstruct
    void init() throws IOException {
        // member
        Member member = new Member();
        member.setNickname("admin");
        member.setEncryptedPassword("admin");
        Member member1 = memberService.save(member);

        member = new Member();
        member.setNickname("jdh6036");
        member.setEncryptedPassword("jdh6036");
        Member member2 = memberService.save(member);

        member = new Member();
        member.setNickname("test");
        member.setEncryptedPassword("test");
        Member member3 = memberService.save(member);

        // board
        Board board1 = boardRepository.save(new Board("메이플랜드 커뮤니티", member2));
        Board board2 = boardRepository.save(new Board("모든 MBTI 모여라 커뮤니티", member2));
        Board board3 = boardRepository.save(new Board("스프링 부트 개발자 커뮤니티", member2));
        Board board4 = boardRepository.save(new Board("아르테일 공식 커뮤니티", member2));
        Board board5 = boardRepository.save(new Board("블랙컴뱃 공식 커뮤니티", member2));

        // category
        Category category1 = categoryRepository.save((new Category("IT")));
        Category category2 = categoryRepository.save((new Category("일상")));
        Category category3 = categoryRepository.save((new Category("음악")));
        Category category4 = categoryRepository.save((new Category("여행")));
        Category category5 = categoryRepository.save((new Category("독서")));


        // post
        for(int i=0; i<300; i++) {
            Post post = new Post("title" + i, "content" + i, board1, false);
            PostCategory postCategory1 = PostCategory.createPostCategory(category1);
            postCategory1.setPost(post);
            PostCategory postCategory2 = PostCategory.createPostCategory(category4);
            postCategory2.setPost(post);
            post.getPostCategories().add(postCategory1);
            post.getPostCategories().add(postCategory2);
            post.setMember(member2);
            Post savedPost = postRepository.save(post);
            board1.getPosts().add(savedPost);
        }
    }
}
