package Application.domain;

import application.domain.Board;
import application.domain.BoardRepository;
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
    private BoardRepository boardRepository;

    @Before
    public void before(){
        boardRepository.deleteAll();

        Board board = Board.builder()
                .title("테스트 게시글입니다.")
                .content("테스트 내용입니다.")
                .writer("gun")
                .hit(0l)
                .build();

        boardRepository.save(board);
    }
    @Test
    public void testBoardInsert(){
        String title = "테스트 게시글입니다.";
        String content = "테스트 내용입니다.";

        Board readBoard = boardRepository.findAll().get(0);

        assertEquals(title, readBoard.getTitle());
        assertEquals(content, readBoard.getContent());
    }
    @Test
    public void testBoardSelect(){
        String title = "테스트 게시글입니다.";
        String content = "테스트 내용입니다.";

        List<Board> list = boardRepository.findAll();

        Board readBoard = list.get(0);

        assertEquals(title, readBoard.getTitle());
        assertEquals(content, readBoard.getContent());
    }
    @Test
    public void testBoardDelete(){
        String title = "테스트 게시글입니다.";
        String content = "테스트 내용입니다.";

        boardRepository.deleteById(1l);
    }
    @Test
    public void testBoardUpdate(){
        String title = "수정된 제목";
        String content = "수정된 내용";

        Board board = boardRepository.findAll().get(0);
        board.update(title, content);
        boardRepository.save(board);

        Board readBoard = boardRepository.findAll().get(0);

        assertEquals(title, readBoard.getTitle());
        assertEquals(content, readBoard.getContent());
    }
}