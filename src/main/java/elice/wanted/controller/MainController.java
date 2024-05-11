package elice.wanted.controller;

import elice.wanted.argumentresolver.Login;
import elice.wanted.dto.BoardResponseDto;
import elice.wanted.entity.Member;
import elice.wanted.service.BoardService;
import elice.wanted.entity.Board;
import elice.wanted.session.MemberSessionInfo;
import elice.wanted.session.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequestMapping("/")
public class MainController {

    private final BoardService boardService;

    @Autowired
    public MainController(BoardService boardService) {
        this.boardService = boardService;
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

    @GetMapping
    public String mainPage(Model model) {
        List<BoardResponseDto> boardResponseDtos = boardService.findAll().stream().map((b) -> b.toResponseDto()).collect(Collectors.toList());

        model.addAttribute("boardResponseDtos", boardResponseDtos);
        return "main";
    }
}
