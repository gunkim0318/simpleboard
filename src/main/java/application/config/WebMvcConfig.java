package application.config;

import com.samskivert.mustache.Mustache;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.mustache.MustacheEnvironmentCollector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * WebMvc 설정 클래스
 */
@Configuration
@RequiredArgsConstructor
public class WebMvcConfig extends WebMvcConfigurerAdapter {
    private final CsrfInterceptor csrfInterceptor;

    /**
     * csrf토큰 처리를 위한 인터셉터
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(csrfInterceptor);
    }

    /**
     * 비밀번호 암호화를 위한 빈
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /**
     * 머스테치 값이 없을 경우 기본 값을 넣어주기 위한 빈
     * @param templateLoader
     * @param environment
     * @return
     */
    @Bean
    public Mustache.Compiler mustacheCompiler(Mustache.TemplateLoader templateLoader, Environment environment) {
        String defaultValue = "";

        MustacheEnvironmentCollector collector = new MustacheEnvironmentCollector();
        collector.setEnvironment(environment);

        return Mustache.compiler()
                .defaultValue(defaultValue)
                .withLoader(templateLoader)
                .withCollector(collector);
    }
}