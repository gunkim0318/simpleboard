package application.config;

import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

/**
 * 머스테치에서 csrf토큰을 사용하기 위한 인터셉터
 */
@Component
@Slf4j
public class CsrfInterceptor extends HandlerInterceptorAdapter {
    @Override
    public void postHandle(HttpServletRequest req, HttpServletResponse res, Object handler, ModelAndView mav) throws Exception {
        if(mav != null){
            CsrfToken csrfToken = (CsrfToken) req.getAttribute("_csrf");
            mav.addObject("_csrf", csrfToken);
        }
    }
}