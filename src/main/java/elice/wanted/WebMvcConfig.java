package elice.wanted;

import elice.wanted.argumentresolver.LoginMemberArgumentResolver;
import elice.wanted.interceptor.ErrorCheckInterceptor;
import elice.wanted.interceptor.LoginCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    // Argument Resoluver for @Login Annotation
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginMemberArgumentResolver());
    }

    // Login Check Interceptor
    // Error Check Interceptor
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginCheckInterceptor())
                .order(1)
                .addPathPatterns(
                        "/posts/**/edit", "/posts/**/delete",
                        "/boards/**/edit", "/boards/**/delete", "/boards/**/write", "/boards/create",
                        "/comments/write/**", "/comments/delete/**");

//        registry.addInterceptor(new ErrorCheckInterceptor())
//                .order(2)
//                .addPathPatterns("/**");

    }


}
