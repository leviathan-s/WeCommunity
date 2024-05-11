package elice.wanted.controller;

import elice.wanted.dto.CommentSaveDto;
import elice.wanted.entity.Comment;
import elice.wanted.entity.Member;
import elice.wanted.entity.Post;
import elice.wanted.service.CommentService;
import elice.wanted.service.PostService;
import elice.wanted.session.MemberSessionInfo;
import elice.wanted.session.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/comments")
@Slf4j
public class CommentController {

    private final PostService postService;
    private final CommentService commentService;

    public CommentController(PostService postService, CommentService commentService) {
        this.postService = postService;
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

    @PostMapping("/write/{postId}")
    public String writeComment(@PathVariable Long postId,
                               @ModelAttribute CommentSaveDto commentSaveDto,
                               HttpServletRequest request,
                               RedirectAttributes redirectAttributes) {


        MemberSessionInfo memberSessionInfo = (MemberSessionInfo) request.getSession().getAttribute(SessionConst.LOGIN_MEMBER);
        commentSaveDto.setMemberSessionInfo(memberSessionInfo);
        commentService.save(postId, commentSaveDto);

        redirectAttributes.addAttribute("postId", postId);
        return "redirect:/posts/{postId}";
    }

    @GetMapping("/{commentId}/delete/{postId}")
    public String delete(@PathVariable("commentId") Long commentId,
                         @PathVariable("postId") Long postId,
                         RedirectAttributes redirectAttributes) {


        commentService.deleteById(commentId);
        redirectAttributes.addAttribute("postId", postId);
        return "redirect:/posts/{postId}";
    }
}
