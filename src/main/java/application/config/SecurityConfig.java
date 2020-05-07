package application.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.header.writers.frameoptions.WhiteListedAllowFromStrategy;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.Arrays;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomUserDetailService customUserDetailService;

    private final PasswordEncoder passwordEncoder;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // 페이지 권한 설정
                .antMatchers("/api/**").hasRole("USER")
                .antMatchers("/post/add").hasRole("USER")
                .antMatchers("/post/modify").hasRole("USER")
                .antMatchers("/").permitAll()
                .antMatchers("/member/signUp").permitAll()
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
                .and()
                    .formLogin()
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
    @Override
    public void configure(WebSecurity web) throws Exception
    {
        // static 디렉터리의 하위 파일 목록은 인증 무시 ( = 항상통과 )
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/lib/**");
    }
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailService).passwordEncoder(passwordEncoder);
    }
}