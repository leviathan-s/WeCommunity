package elice.wanted.controller;

import elice.wanted.dto.CommentSaveDto;
import elice.wanted.dto.PostResponseDto;
import elice.wanted.dto.PostUpdateDto;
import elice.wanted.entity.*;
import elice.wanted.service.CategoryService;
import elice.wanted.service.CommentService;
import elice.wanted.service.PostService;
import elice.wanted.session.MemberSessionInfo;
import elice.wanted.session.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;
    private final CategoryService categoryService;
    private final CommentService commentService;

    public PostController(PostService postService, CategoryService categoryService, CommentService commentService) {
        this.postService = postService;
        this.categoryService = categoryService;
        this.commentService = commentService;
    }

    @ModelAttribute("memberSessionInfo")
    public MemberSessionInfo loginMember(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if(session == null)
            return null;
        else {
            MemberSessionInfo memberInfo = (MemberSessionInfo)session.getAttribute(SessionConst.LOGIN_MEMBER);
            if (memberInfo == null) {
                return null;
            }

            return memberInfo;
        }
    }

    // 게시글 조회
    @GetMapping("/{postId}")
    public String viewPost(@PathVariable Long postId, Model model) {
        // 조회수 하나 증가
        Post findPost = postService.findByIdThroughView(postId);

        PostResponseDto postResponseDto = findPost.toPostResponseDto();

        Long boardId = findPost.getBoard().getId();
        List<Comment> comments = commentService.findByPost(findPost);
        List<String> categories = categoryService.findAll().stream().map((c) -> c.getName()).collect(Collectors.toList());

        model.addAttribute("commentSaveDto", new CommentSaveDto());
        model.addAttribute("commentResponseDtos", postResponseDto.getCommentResponseDtos());
        model.addAttribute("boardId", boardId);
        model.addAttribute("postResponseDto", postResponseDto);
        model.addAttribute("categories", categories);

        return "/posts/postDetail";
    }

    // 게시글 수정 폼
    @GetMapping("/{postId}/edit")
    public String editPostForm(@PathVariable Long postId, Model model) {
        List<String> categories = categoryService.findAll().stream().map((c) -> c.getName()).collect(Collectors.toList());
        Post findPost = postService.findById(postId);

        // post를 post update dto로 변환
        PostUpdateDto postUpdateDto = findPost.toPostUpdateDto();
        // 이것을 모델에 담아 뷰로 전달
        model.addAttribute("postUpdateDto", postUpdateDto);
        model.addAttribute("categories", categories);
        return "/posts/edit";
    }

    // 게시글 수정 로직
    @PostMapping("/{postId}/edit")
    public String editPost(@PathVariable Long postId,
                           @Validated @ModelAttribute PostUpdateDto postUpdateDto,
                           BindingResult bindingResult,
                           Model model,
                           RedirectAttributes redirectAttributes) {

        // 입력 값 검증 로직
        if (bindingResult.hasErrors()) {
            List<String> categories = categoryService.findAll().stream().map((c) -> c.getName()).collect(Collectors.toList());
            model.addAttribute("categories", categories);
            return "/posts/edit";
        }

        Post editedPost = postService.edit(postId, postUpdateDto);
        redirectAttributes.addAttribute("postId", editedPost.getId());
        return "redirect:/posts/{postId}";
    }


    // 게시글 삭제 폼
    @GetMapping("/{postId}/delete")
    public String deletePostForm() {
        return "/posts/delete";
    }

    // 게시글 삭제 로직
    @PostMapping("/{postId}/delete")
    public String deletePost(@PathVariable Long postId, RedirectAttributes redirectAttributes) {
        Post post = postService.findById(postId);
        Long boardId = post.getBoard().getId();
        postService.delete(post);
        redirectAttributes.addAttribute("boardId", boardId);
        return "redirect:/boards/{boardId}";
    }
}
