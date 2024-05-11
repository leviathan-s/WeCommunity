package elice.wanted.controller;

import elice.wanted.dto.*;
import elice.wanted.entity.*;
import elice.wanted.exception.NoSuchBoardException;
import elice.wanted.service.BoardService;
import elice.wanted.service.CategoryService;
import elice.wanted.service.PostService;
import elice.wanted.session.MemberSessionInfo;
import elice.wanted.session.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequestMapping("/boards")
public class BoardController {
    private final BoardService boardService;
    private final CategoryService categoryService;
    private final PostService postService;

    @Autowired
    public BoardController(BoardService boardService, CategoryService categoryService, PostService postService) {
        this.boardService = boardService;
        this.categoryService = categoryService;
        this.postService = postService;
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

    @GetMapping("/{boardId}")
    public String boardMain(@PathVariable Long boardId,
                            @RequestParam(value = "page", defaultValue = "0") int page,
                            @RequestParam(value = "searchBy") String searchBy,
                            @RequestParam(value = "searchKeyword") String searchKeyword,
                            @RequestParam(value = "sortBy") String sortBy,
                            Model model) throws IOException {

        Board board = boardService.findById(boardId);
        BoardResponseDto boardResponseDto = board.toResponseDto();

        PageRequest pageRequest = PageRequest.of(page, 10);
        Page<Post> pagedPosts = postService.findByBoardPaging(board, pageRequest);

        List<PostResponseDto> postResponseDtos = pagedPosts.getContent().stream().map((p) -> p.toPostResponseDto()).collect(Collectors.toList());
        int currentPage = pagedPosts.getNumber();
        int totalPages = pagedPosts.getTotalPages();

        model.addAttribute("page", page);
        model.addAttribute("maxPage", 10);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("postResponseDtos", postResponseDtos);
        model.addAttribute("boardResponseDto", boardResponseDto);
        return "boards/boardMain";
    }

    // 게시판 생성 페이지
    @GetMapping("/create")
    public String saveBoardForm(Model model) {
        BoardSaveDto boardSaveDto = new BoardSaveDto();
        model.addAttribute("boardSaveDto", boardSaveDto);
        return "boards/create";
    }


    // 게시판 생성 로직
    @PostMapping("/create")
    public String saveBoard(@Validated @ModelAttribute("boardSaveDto") BoardSaveDto boardSaveDto,
                            BindingResult bindingResult,
                            HttpServletRequest request,
                            RedirectAttributes redirectAttributes) throws IOException {

        // 입력값 validation
        if (bindingResult.hasErrors()) {
            return "/boards/create";
        }

        boardSaveDto.setMemberSessionInfo((MemberSessionInfo) request.getSession().getAttribute(SessionConst.LOGIN_MEMBER));
        boardService.save(boardSaveDto);
        redirectAttributes.addAttribute("create_status", true);
        return "redirect:/";
    }

    // 게시판 수정 페이지
    @GetMapping("/{boardId}/edit")
    public String editBoardForm(@PathVariable Long boardId, Model model) {
        BoardUpdateDto boardUpdateDto = boardService.findById(boardId).toUpdateDto();
        model.addAttribute("id", boardId);
        model.addAttribute("boardUpdateDto", boardUpdateDto);
        return "boards/edit";
    }

    // 게시판 수정 로직
    @PostMapping("/{boardId}/edit")
    public String editBoard(@PathVariable Long boardId,
                            @Validated @ModelAttribute BoardUpdateDto boardUpdateDto,
                            BindingResult bindingResult,
                            HttpServletRequest request,
                            RedirectAttributes redirectAttributes) {

        // 입력값 검증로직
        if (bindingResult.hasErrors()) {
            return "boards/edit";
        }

        // 게시판 삭제를 요청한 유저의 세션 정보 획득
        HttpSession session = request.getSession();
        MemberSessionInfo memberSessionInfo = (MemberSessionInfo) session.getAttribute(SessionConst.LOGIN_MEMBER);
        Long requestMemberId = memberSessionInfo.getId();
        Long boardOwnerId = boardService.findById(boardId).getMember().getId();

        // 적합한 사용자만 수정 가능
        if(isAuthenticated(boardOwnerId, requestMemberId)) {
            boardUpdateDto.setId(boardId);
            boardService.edit(boardUpdateDto);
            redirectAttributes.addAttribute("boardEditSuccess", true);
        } else {
            redirectAttributes.addAttribute("boardEditFailed", true);
        }
        return "redirect:/";
    }


    private boolean isAuthenticated(Long ownerId, Long userId) {
        if (userId.equals(ownerId) || userId == 1) {
            return true;
        } else {
            return false;
        }
    }

    // 게시판 삭제 로직
    @GetMapping("/{boardId}/delete")
    public String deleteBoardForm(@PathVariable Long boardId,
                                  Model model,
                                  HttpServletRequest request,
                                  RedirectAttributes redirectAttributes) {

        // 게시판 삭제를 요청한 유저의 세션 정보 획득
        HttpSession session = request.getSession();
        MemberSessionInfo memberSessionInfo = (MemberSessionInfo) session.getAttribute(SessionConst.LOGIN_MEMBER);
        Long requestMemberId = memberSessionInfo.getId();
        Long boardOwnerId = boardService.findById(boardId).getMember().getId();


        // 게시판의 주인이거나 admin계정만 삭제가 가능하다.
        if (isAuthenticated(boardOwnerId, requestMemberId)) {
            boardService.delete(boardId);
            redirectAttributes.addAttribute("boardDeletionSuccess", true);
        } else {
            redirectAttributes.addAttribute("boardDeletionFailed", true);
        }

        return "redirect:/";
    }


    // 게시판 글쓰기
    @GetMapping("/{boardId}/write")
    public String writePostForm(@PathVariable Long boardId, Model model) {
        List<String> categories = categoryService.findAll().stream().map((c) -> c.getName()).collect(Collectors.toList());
        model.addAttribute("postWriteDto", new PostWriteDto());
        model.addAttribute("categories", categories);
        return "boards/write";
    }

    // 게시판 글쓰기
    @PostMapping("/{boardId}/write")
    public String writePost(@PathVariable Long boardId,
                            @Validated @ModelAttribute PostWriteDto postWriteDto,
                            BindingResult bindingResult,
                            HttpServletRequest request,
                            Model model,
                            RedirectAttributes redirectAttributes) {
        // 입력 값 검증로직
        if (bindingResult.hasErrors()) {
            List<String> categories = categoryService.findAll().stream().map((c) -> c.getName()).collect(Collectors.toList());
            model.addAttribute("categories", categories);
            return "boards/write";
        }

        postWriteDto.setMemberSessionInfo((MemberSessionInfo) request.getSession().getAttribute(SessionConst.LOGIN_MEMBER));
        postWriteDto.setBoardId(boardId);
        Post savedPost = postService.save(postWriteDto);
        redirectAttributes.addAttribute("postId", savedPost.getId());
        return "redirect:/posts/{postId}";
    }

}
