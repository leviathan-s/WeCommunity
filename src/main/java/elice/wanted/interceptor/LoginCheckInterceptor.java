package elice.wanted.interceptor;

import elice.wanted.session.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 만약 세션이 없거나, 발급받은 세션에 로그인 정보가 없다면 로그인 페이지로 리다이렉션 시킨다.
        HttpSession session = request.getSession();

        if (session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
            log.info("Unauthorized Access. Redirect to login page");
            response.sendRedirect("/members/login");
            return false;
        }
        // 만약 세션이 있다면 그대로 controller를 호출한다.
        return true;
    }
}
