package application.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 로그인 성공 시 핸들링을 위한 클래스
 */
@Slf4j
@Configuration
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    /**
     * 로그인 성공 시 실행
     * @param req
     * @param res
     * @param auth
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse res, Authentication auth) throws IOException, ServletException {
      log.debug("로그인 성공");

      log.debug("auth : "+auth.getPrincipal().toString());
    }
}