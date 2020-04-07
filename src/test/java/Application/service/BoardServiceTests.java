package Application.service;

import application.domain.Board;
import application.domain.BoardRepository;
import application.dto.BoardRequestDTO;
import application.dto.BoardResponseDTO;
import application.service.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class BoardServiceTests {
    @Autowired
    private BoardService boardService;

    @Autowired
    private BoardRepository boardRepository;

    @Before
    public void before(){
        boardRepository.deleteAll();

        Board board = Board.builder()
                .title("제목 테스트")
                .content("내용 테스트")
                .writer("안녕")
                .build();

        boardRepository.save(board);
    }

    @Test
    public void testBoardInsert(){
        BoardRequestDTO dto = BoardRequestDTO.builder()
                .title("제목 테스트")
                .content("내용 테스트")
                .writer("안녕")
                .build();

        boardService.insertBoard(dto);

        Board board = boardRepository.findAll().get(1);
        assertEquals(board.getTitle(), dto.getTitle());
        assertEquals(board.getContent(), dto.getContent());
        assertEquals(board.getWriter(), dto.getWriter());
    }
    @Test
    public void testBoardUpdate(){
        BoardRequestDTO updateDto = BoardRequestDTO.builder()
                .title("수정된 제목")
                .content("수정된 내용")
                .build();

        Long updateId = boardService.selectBoardList().get(0).getId();
        boardService.updateBoard(updateId, updateDto);


        Board board = boardRepository.findAll().get(0);
        assertEquals(updateDto.getTitle(), board.getTitle());
        assertEquals(updateDto.getContent(), board.getContent());
    }
    @Test
    public void testBoardListSelect(){
        String title = "제목 테스트";
        String content = "내용 테스트";

        BoardResponseDTO responseDTO = boardService.selectBoardList().get(0);

        assertEquals(responseDTO.getTitle(), title);
        assertEquals(responseDTO.getContent(), content);
    }
    @Test
    public void testBoardDelete(){
        BoardResponseDTO responseDTO = boardService.selectBoardList().get(0);

        boardService.deleteBoard(responseDTO.getId());

        if(boardRepository.findAll().size() > 0){
            fail("삭제 실패");
        }
    }
}