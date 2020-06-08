package application.config;

import application.jpa.enums.Role;
import application.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.header.writers.frameoptions.WhiteListedAllowFromStrategy;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.Arrays;

/**
 * 스프링 시큐리티 설정
 */
@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomUserDetailsService customUserDetailService;

    private final LoginSuccessHandler loginSuccessHandler;

    private final LoginFailHandler loginFailHandler;

    private final PasswordEncoder passwordEncoder;

    /**
     * 시큐리티 URL 설정
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // 페이지 권한 설정
                .antMatchers("/posts/add", "posts/insert", "posts/delete", "posts/modify").hasAnyRole(Role.USER.name(), Role.ADMIN.name())
                .antMatchers("/").permitAll()
                .antMatchers("/member/signUp").permitAll()
                //여기부터는 개발 환경 시 h2-console 사용하기 위한 설정
                .antMatchers("/test_db/**").permitAll()
                .and()
                    .csrf().ignoringAntMatchers("/test_db/**")
                    .and()
                    .headers()
                    .addHeaderWriter(
                            new XFrameOptionsHeaderWriter(
                                    new WhiteListedAllowFromStrategy(Arrays.asList("localhost"))    // 여기!
                            )
                    )
                    .frameOptions().sameOrigin()
                //여기까지
                .and()
                    .formLogin()
                    .successHandler(loginSuccessHandler)
                    .failureHandler(loginFailHandler)
                    .loginPage("/member/signIn")
                    .loginProcessingUrl("/member/signIn")
                    .usernameParameter("email")
                    .defaultSuccessUrl("/")
                    .permitAll()
                .and()
                    .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
                    .logoutSuccessUrl("/")
                    .invalidateHttpSession(true);
    }

    /**
     * static 디렉터리의 하위 파일 목록은 인증 무시 설정
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception
    {
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/lib/**");
    }

    /**
     * 사용할 UserDetailsService, PasswordEncoder 설정
     * @param auth
     * @throws Exception
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailService).passwordEncoder(passwordEncoder);
    }
}