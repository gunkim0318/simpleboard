package Application.domain;

import application.domain.*;
import application.domain.Posts;
import application.dto.PageDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class PostsRepositoryTests {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Before
    public void before(){
        postRepository.deleteAll();
        memberRepository.deleteAll();

        Member member = Member.builder()
                .email("gunkim0318@gmail.com")
                .password("rlarjs123")
                .nickname("테스트")
                .gender(Gender.M)
                .role(Role.ADMIN)
                .build();

        memberRepository.save(member);

        Posts board = Posts.builder()
                .title("테스트 게시글입니다.")
                .content("테스트 내용입니다.")
                .member(member)
                .hit(0l)
                .build();

        postRepository.save(board);
    }
    @Test
    public void testPostInsert(){
        String title = "테스트 게시글입니다.";
        String content = "테스트 내용입니다.";

        Posts findPosts = postRepository.findAll().get(0);

        log.info(findPosts.toString());
        assertEquals(title, findPosts.getTitle());
        assertEquals(content, findPosts.getContent());
    }
    @Test
    public void testPostDelete(){
        Posts findPosts = postRepository.findAll().get(0);
        postRepository.delete(findPosts);
    }
    @Test
    public void testPostUpdate(){
        String title = "수정된 제목";
        String content = "수정된 내용";

        Posts board = postRepository.findAll().get(0);
        board.update(title, content);
        postRepository.save(board);

        Posts readBoard = postRepository.findAll().get(0);

        assertEquals(title, readBoard.getTitle());
        assertEquals(content, readBoard.getContent());
    }
    @Test
    public void testPostPaging(){
        //for (1 ~ 154)
        IntStream.rangeClosed(1, 154).forEach(i -> {
            Member member = Member.builder()
                    .email("gunkim"+i+"@gmail.com")
                    .password("rlarjs123")
                    .nickname("테스트")
                    .gender(Gender.M)
                    .role(Role.ADMIN)
                    .build();
            memberRepository.save(member);
            Posts board = Posts.builder()
                    .title("테스트 게시글입니다.")
                    .content("테스트 내용입니다.")
                    .member(member)
                    .hit(0l)
                    .build();

            postRepository.save(board);
        });
        PageDTO pagingUtil = new PageDTO();
        pagingUtil.setPageNum(4);
        postRepository.findAll(pagingUtil.toEntity()).stream()
                .forEach(post -> {
                       log.info("post : "+post);
        });
    }
}