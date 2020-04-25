package Application.domain;

import application.domain.Post;
import application.domain.PostRepository;
import application.domain.MemberRepository;
import application.domain.Member;
import application.domain.Role;
import application.domain.Gender;
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
public class PostRepositoryTests {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Before
    public void before(){
        postRepository.deleteAll();

        Member member = Member.builder()
                .email("gunkim0318@gmail.com")
                .password("rlarjs123")
                .nickname("테스트")
                .gender(Gender.M)
                .role(Role.ADMIN)
                .build();

        memberRepository.save(member);

        Post board = Post.builder()
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

        Post findPost = postRepository.findAll().get(0);

        log.info(findPost.toString());
        assertEquals(title, findPost.getTitle());
        assertEquals(content, findPost.getContent());
    }
    @Test
    public void testPostDelete(){
        Post findPost = postRepository.findAll().get(0);
        postRepository.delete(findPost);
    }
    @Test
    public void testPostUpdate(){
        String title = "수정된 제목";
        String content = "수정된 내용";

        Post board = postRepository.findAll().get(0);
        board.update(title, content);
        postRepository.save(board);

        Post readBoard = postRepository.findAll().get(0);

        assertEquals(title, readBoard.getTitle());
        assertEquals(content, readBoard.getContent());
    }
    @Test
    public void testPostPaging(){
        //for (1 ~ 154)
        IntStream.rangeClosed(1, 154).forEach(i -> {
            Member member = Member.builder()
                    .email("gunkim0318@gmail.com")
                    .password("rlarjs123")
                    .nickname("테스트")
                    .gender(Gender.M)
                    .role(Role.ADMIN)
                    .build();
            memberRepository.save(member);
            Post board = Post.builder()
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