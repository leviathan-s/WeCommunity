package elice.wanted.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error")
public class ErrorController {

    @GetMapping("/404")
    public String notFound() {
        return "/error/404";
    }

    @GetMapping("/500")
    public String serverInternal() {
        return "/error/500";
    }
}
