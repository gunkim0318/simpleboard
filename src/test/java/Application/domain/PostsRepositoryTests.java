package Application.domain;

import application.jpa.domain.Member;
import application.jpa.domain.Posts;
import application.jpa.enums.Gender;
import application.jpa.enums.Role;
import application.jpa.repository.MemberRepository;
import application.jpa.repository.PostsRepository;
import application.web.dto.PageRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

@Slf4j
@RunWith(SpringRunner.class)
@DataJpaTest
public class PostsRepositoryTests {
    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Before
    public void before(){
        postsRepository.deleteAll();
        memberRepository.deleteAll();

        Member member = Member.builder()
                .email("gunkim0318@gmail.com")
                .password("rlarjs123")
                .nickname("테스트")
                .gender(Gender.M)
                .role(Role.ADMIN)
                .build();

        memberRepository.save(member);

        Posts posts = Posts.builder()
                .title("테스트 게시글입니다.")
                .content("테스트 내용입니다.")
                .member(member)
                .hit(0l)
                .build();

        postsRepository.save(posts);
    }
    @Test
    public void testPostsInsert(){
        String title = "테스트 게시글입니다.";
        String content = "테스트 내용입니다.";

        Posts findPosts = postsRepository.findAll().get(0);

        log.info(findPosts.toString());
        assertEquals(title, findPosts.getTitle());
        assertEquals(content, findPosts.getContent());
    }
    @Test
    public void testPostsDelete(){
        Posts findPosts = postsRepository.findAll().get(0);
        postsRepository.delete(findPosts);
    }
    @Test
    public void testPostsUpdate(){
        String title = "수정된 제목";
        String content = "수정된 내용";

        Posts posts = postsRepository.findAll().get(0);
        posts.update(title, content);
        postsRepository.save(posts);

        Posts readPosts = postsRepository.findAll().get(0);

        assertEquals(title, readPosts.getTitle());
        assertEquals(content, readPosts.getContent());
    }
    @Test
    public void testPostsPaging(){
        Member member = memberRepository.findAll().get(0);
        IntStream.rangeClosed(1, 154).forEach(i -> {
            Posts posts = Posts.builder()
                    .title("테스트 게시글입니다.")
                    .content("테스트 내용입니다.")
                    .member(member)
                    .hit(0l)
                    .build();

            postsRepository.save(posts);
        });
        PageRequestDTO pagingUtil = new PageRequestDTO();
        pagingUtil.setPageNum(4);
        postsRepository.findAll(pagingUtil.toEntity()).stream()
                .forEach(posts -> {
                       log.info("post : "+posts);
        });
    }
}