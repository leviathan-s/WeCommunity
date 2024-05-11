package elice.wanted.controller;

import elice.wanted.dto.LoginForm;
import elice.wanted.dto.MemberSaveDto;
import elice.wanted.entity.Member;
import elice.wanted.exception.LoginFailException;
import elice.wanted.service.MemberService;
import elice.wanted.session.MemberSessionInfo;
import elice.wanted.session.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Slf4j
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

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

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }


    @GetMapping("/login")
    public String loginForm(Model model) {
        LoginForm loginForm = new LoginForm();
        model.addAttribute("loginForm", loginForm);
        return "members/login";
    }

    @PostMapping("/login")
    public String login(@Validated @ModelAttribute LoginForm loginForm,
                        BindingResult bindingResult,
                        RedirectAttributes redirectAttributes,
                        HttpServletRequest request) {


        // 아이디와 비밀번호 검증 오류 처리
        if (bindingResult.hasErrors()) {
            log.info("Login form not valid");
            return "members/login";
        }

        // 아이디 기반 검색
        String nickname = loginForm.getNickname();
        String encryptedPassword = loginForm.getEncryptedPassword();

        try {
            // Id Check
            Member byNickname = memberService.findByNickname(nickname);

            // After Id Check, Password Check
            Member byEncryptedPassword = memberService.findByNameAndEncryptedPassword(nickname, encryptedPassword);

            // Session
            HttpSession session = request.getSession(true);
            session.setAttribute(SessionConst.LOGIN_MEMBER, byEncryptedPassword.toMemberSessionInfo());

            // Redirect
            return "redirect:/";
        } catch (Exception e) {
            redirectAttributes.addAttribute("loginFail", true);
            return "redirect:/members/login";
        }

    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        return "redirect:/";
    }

    @GetMapping("/join")
    public String joinForm(Model model) {
        MemberSaveDto memberSaveDto = new MemberSaveDto();
        model.addAttribute("memberSaveDto", memberSaveDto);
        return "members/join";
    }

    @PostMapping("/join")
    public String join(@Validated @ModelAttribute MemberSaveDto memberSaveDto,
                       BindingResult bindingResult,
                       Model model) {
        
        // 회원가입 폼에 대한 검증오류 처리
        if (bindingResult.hasErrors()) {
            return "/members/join";
        }
        
        Member member = memberSaveDto.toEntity();
        memberService.save(member);
        return "redirect:/";
    }



}
