package Application.web;

import application.domain.Post;
import application.domain.PostRepository;
import application.dto.PostRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Slf4j
public class BoardControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;

    @Before
    public void before() {
        postRepository.deleteAll();

        Post board = Post.builder()
                .title("제목 테스트")
                .content("내용 테스트")
                .writer("안녕")
                .build();
        postRepository.save(board);
    }
    @Test
    public void testBoardInsert() throws Exception {
        String title = "제목입니다.";
        String content = "내용입니다.";
        String writer = "사용자";

        PostRequestDTO dto = PostRequestDTO.builder()
                .title(title)
                .content(content)
                .writer(writer)
                .build();

        mockMvc.perform((post("/api/board/insert")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(dto))
        )).andExpect(status().isOk());

        Post board = postRepository.findAll().get(1);
        assertEquals(title, board.getTitle());
        assertEquals(content, board.getContent());
        assertEquals(writer, board.getWriter());
    }
    @Test
    public void testBoardUpdate() throws Exception {
        PostRequestDTO dto = PostRequestDTO.builder()
                .title("수정된 제목")
                .content("수정된 내용")
                .build();


        Long bno = postRepository.findAll().get(0).getId();
        String requestUrl = "/api/board/update/"+bno;

        mockMvc.perform((put(requestUrl)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(dto))
        )).andExpect(status().isOk());

        Post board = postRepository.findAll().get(0);
        assertEquals(dto.getTitle(), board.getTitle());
        assertEquals(dto.getContent(), board.getContent());
    }
    @Test
    public void testBoardList() throws Exception {
        mockMvc.perform(get("/api/board/list"))
                .andExpect(status().isOk())
                .andDo(print());
    }
    @Test
    public void testBoardDelete() throws Exception {
        Long bno = postRepository.findAll().get(0).getId();
        mockMvc.perform(delete("/api/board/delete/"+bno))
                .andExpect(status().isOk())
                .andDo(print());

        if(postRepository.findAll().size() > 0){
            fail("삭제 실패");
        }
    }
}