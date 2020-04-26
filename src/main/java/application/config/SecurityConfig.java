package application.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // 페이지 권한 설정
                .antMatchers("/api/**").hasRole("USER")
                .antMatchers("/post/add").hasRole("USER")
                .antMatchers("/post/modify").hasRole("USER")
                .antMatchers("/").permitAll()
                .antMatchers("/member/signUp").permitAll()
                .and() // 로그인 설정
                .formLogin()
                .loginPage("/member/signIn")
                .defaultSuccessUrl("/")
                .usernameParameter("email");
    }
    @Override
    public void configure(WebSecurity web) throws Exception
    {
        // static 디렉터리의 하위 파일 목록은 인증 무시 ( = 항상통과 )
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/lib/**");
    }
}