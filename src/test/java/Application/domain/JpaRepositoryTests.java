package Application.domain;

import application.domain.Post;
import application.domain.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class JpaRepositoryTests {
    @Autowired
    private PostRepository postRepository;

    @Before
    public void before(){
        postRepository.deleteAll();

        Post board = Post.builder()
                .title("테스트 게시글입니다.")
                .content("테스트 내용입니다.")
                .writer("gun")
                .hit(0l)
                .build();

        postRepository.save(board);
    }
    @Test
    public void testBoardInsert(){
        String title = "테스트 게시글입니다.";
        String content = "테스트 내용입니다.";

        Post readBoard = postRepository.findAll().get(0);

        assertEquals(title, readBoard.getTitle());
        assertEquals(content, readBoard.getContent());
    }
    @Test
    public void testBoardSelect(){
        String title = "테스트 게시글입니다.";
        String content = "테스트 내용입니다.";

        List<Post> list = postRepository.findAll();

        Post readBoard = list.get(0);

        assertEquals(title, readBoard.getTitle());
        assertEquals(content, readBoard.getContent());
    }
    @Test
    public void testBoardDelete(){
        String title = "테스트 게시글입니다.";
        String content = "테스트 내용입니다.";

        postRepository.deleteById(1l);
    }
    @Test
    public void testBoardUpdate(){
        String title = "수정된 제목";
        String content = "수정된 내용";

        Post board = postRepository.findAll().get(0);
        board.update(title, content);
        postRepository.save(board);

        Post readBoard = postRepository.findAll().get(0);

        assertEquals(title, readBoard.getTitle());
        assertEquals(content, readBoard.getContent());
    }
}