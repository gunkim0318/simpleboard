package Application.service;

import application.domain.Post;
import application.domain.PostRepository;
import application.dto.PostRequestDTO;
import application.dto.PostResponseDTO;
import application.service.PostService;
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
    private PostService postService;

    @Autowired
    private PostRepository boardRepository;

    @Before
    public void before(){
        boardRepository.deleteAll();

        Post board = Post.builder()
                .title("제목 테스트")
                .content("내용 테스트")
                .writer("안녕")
                .build();

        boardRepository.save(board);
    }

    @Test
    public void testBoardInsert(){
        PostRequestDTO dto = PostRequestDTO.builder()
                .title("제목 테스트")
                .content("내용 테스트")
                .writer("안녕")
                .build();

        postService.insertPost(dto);

        Post board = boardRepository.findAll().get(1);
        assertEquals(board.getTitle(), dto.getTitle());
        assertEquals(board.getContent(), dto.getContent());
        assertEquals(board.getWriter(), dto.getWriter());
    }
    @Test
    public void testBoardUpdate(){
        PostRequestDTO updateDto = PostRequestDTO.builder()
                .title("수정된 제목")
                .content("수정된 내용")
                .build();

        Long updateId = postService.selectBoardList().get(0).getId();
        postService.updatePost(updateId, updateDto);


        Post board = boardRepository.findAll().get(0);
        assertEquals(updateDto.getTitle(), board.getTitle());
        assertEquals(updateDto.getContent(), board.getContent());
    }
    @Test
    public void testBoardListSelect(){
        String title = "제목 테스트";
        String content = "내용 테스트";

        PostResponseDTO responseDTO = postService.selectBoardList().get(0);

        assertEquals(responseDTO.getTitle(), title);
        assertEquals(responseDTO.getContent(), content);
    }
    @Test
    public void testBoardDelete(){
        PostResponseDTO responseDTO = postService.selectBoardList().get(0);

        postService.deletePost(responseDTO.getId());

        if(boardRepository.findAll().size() > 0){
            fail("삭제 실패");
        }
    }
}