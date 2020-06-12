package application;

import application.jpa.domain.Member;
import application.jpa.domain.Posts;
import application.jpa.enums.Gender;
import application.jpa.enums.Role;
import application.jpa.repository.MemberRepository;
import application.jpa.repository.PostsRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.SQLException;
import java.util.stream.IntStream;

@EnableJpaAuditing //Auditing 활성화
@SpringBootApplication
public class Application {
    public static void main(String[] args){
        SpringApplication.run(Application.class, args);
    }
    @Bean
    public CommandLineRunner runner(MemberRepository memberRepository, PostsRepository postsRepository, PasswordEncoder passwordEncoder) throws SQLException {
        return (args) -> {
            Member adminMember = memberRepository.save(Member.builder()
            .email("gunkim0318@gmail.com")
            .password(passwordEncoder.encode("test"))
            .nickname("gunkim")
            .gender(Gender.M)
            .role(Role.ADMIN)
            .build());

            Member userMember = memberRepository.save(Member.builder()
                    .email("gunkim@gmail.com")
                    .password(passwordEncoder.encode("test"))
                    .nickname("gun")
                    .gender(Gender.F)
                    .role(Role.USER)
                    .build());

            IntStream.rangeClosed(1, 200).forEach(i -> {
                  postsRepository.save(Posts.builder()
                  .title(i+"번째 게시글입니다")
                  .content("게시글 내용입니다."+i)
                  .member(userMember)
                  .hit(0l)
                  .build());
            });
        };
    }
}
