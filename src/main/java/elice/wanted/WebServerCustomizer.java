package elice.wanted;

import elice.wanted.exception.NoSuchBoardException;
import elice.wanted.exception.NoSuchCommentException;
import elice.wanted.exception.NoSuchPostException;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.stereotype.Component;


// For Exception to customized Error page!
@Component
public class WebServerCustomizer implements WebServerFactoryCustomizer<ConfigurableWebServerFactory> {
    @Override
    public void customize(ConfigurableWebServerFactory factory) {
        ErrorPage errorPage1 = new ErrorPage(NoSuchBoardException.class, "/error/404");
        ErrorPage errorPage2 = new ErrorPage(NoSuchPostException.class, "/error/404");
        ErrorPage errorPage3 = new ErrorPage(NoSuchCommentException.class, "/error/404");

        factory.addErrorPages(errorPage1, errorPage2, errorPage3);
    }
}
