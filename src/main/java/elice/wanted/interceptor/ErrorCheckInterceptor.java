package elice.wanted.interceptor;

import elice.wanted.exception.NoSuchBoardException;
import elice.wanted.exception.NoSuchCommentException;
import elice.wanted.exception.NoSuchPostException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;


// Deprecated!
@Slf4j
public class ErrorCheckInterceptor implements HandlerInterceptor {
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        if(ex == null)
            return;
        else if(ex instanceof NoSuchBoardException) {
            log.info("해당 게시물이 존재하지 않습니다.");
            response.sendError(404, "해당 게시물이 존재하지 않습니다.");
        } else if (ex instanceof NoSuchPostException) {
            response.sendError(404, "해당 게시글이 존재하지 않습니다.");
        } else if(ex instanceof NoSuchCommentException) {
            response.sendError(404, "해당 댓글이 존재하지 않습니다.");
        }
    }
}
