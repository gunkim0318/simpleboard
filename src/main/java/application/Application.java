package application;

import application.jpa.domain.Member;
import application.jpa.domain.Posts;
import application.jpa.domain.Reply;
import application.jpa.enums.Gender;
import application.jpa.enums.Role;
import application.jpa.repository.MemberRepository;
import application.jpa.repository.PostsRepository;
import application.jpa.repository.ReplyRepository;
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
    public CommandLineRunner runner(MemberRepository memberRepository, PostsRepository postsRepository, ReplyRepository replyRepository, PasswordEncoder passwordEncoder) throws SQLException {
        return (args) -> {
            Member adminMember = memberRepository.save(Member.builder()
            .email("gunkim0318@gmail.com")
            .password(passwordEncoder.encode("test12!@"))
            .nickname("gunkim")
            .gender(Gender.M)
            .role(Role.ADMIN)
            .build());

            Member userMember = memberRepository.save(Member.builder()
                    .email("gunkim@gmail.com")
                    .password(passwordEncoder.encode("test12!@"))
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
            Posts posts = postsRepository.findAll().get(199);
            Member member = memberRepository.findAll().get(0);
            IntStream.rangeClosed(1, 200).forEach(i -> {
                replyRepository.save(Reply.builder()
                        .content(i+"안녕하십니꺼")
                        .member(member)
                        .posts(posts)
                        .build());
            });
        };
    }
}
