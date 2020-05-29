package application.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 머스테치에서 csrf토큰을 사용하기 위한 인터셉터
 */
@Component
@Slf4j
public class CsrfInterceptor extends HandlerInterceptorAdapter {
    /**
     * 매번 컨트롤러 요청 후 model에 csrf토큰값을 추가함
     * @param req
     * @param res
     * @param handler
     * @param mav
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest req, HttpServletResponse res, Object handler, ModelAndView mav) throws Exception {
        if(mav != null){
            CsrfToken csrfToken = (CsrfToken) req.getAttribute("_csrf");
            mav.addObject("_csrf", csrfToken);
        }
    }
}