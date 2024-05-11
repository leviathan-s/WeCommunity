package elice.wanted.exception.handler;

import elice.wanted.exception.NoSuchBoardException;
import elice.wanted.exception.NoSuchCommentException;
import elice.wanted.exception.NoSuchPostException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NoSuchBoardException.class)
    public String handleNoSuchPostException(NoSuchBoardException ex) {
        return "redirect:/error/404";
    }

    @ExceptionHandler(NoSuchPostException.class)
    public String handleNoSuchPostException(NoSuchPostException ex) {
        return "redirect:/error/404";
    }

    @ExceptionHandler(NoSuchCommentException.class)
    public String handleNoSuchCommentException(NoSuchCommentException ex) {
        return "redirect:/error/404";
    }

}
