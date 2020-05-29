//package Application.web;
//
//import application.jpa.domain.*;
//import application.web.request.PostsRequestDTO;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static org.junit.Assert.*;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
//@AutoConfigureMockMvc
//@Slf4j
//public class PostsControllerTests {
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private PostRepository postRepository;
//
//    @Autowired
//    private MemberRepository memberRepository;
//
//    @Before
//    public void before() {
//        postRepository.deleteAll();
//        memberRepository.deleteAll();
//
//        Member member = Member.builder()
//                .email("gunkim0318@gmail.com")
//                .password("rlarjs123")
//                .nickname("관리자")
//                .gender(Gender.M)
//                .role(Role.ADMIN)
//                .build();
//        memberRepository.save(member);
//
//        Posts board = Posts.builder()
//                .title("제목 테스트")
//                .content("내용 테스트")
//                .member(member)
//                .build();
//
//        postRepository.save(board);
//    }
//    @Test
//    @WithMockUser(username = "gunkim0318@gmail.com", roles = "USER")
//    public void testBoardInsert() throws Exception {
//        String title = "제목입니다.";
//        String content = "내용입니다.";
//        String writer = "사용자";
//
//        PostsRequestDTO dto = PostsRequestDTO.builder()
//                .title(title)
//                .content(content)
//                .build();
//
//        mockMvc.perform((post("/api/board/insert")
//                .contentType(MediaType.APPLICATION_JSON_UTF8)
//                .content(new ObjectMapper().writeValueAsString(dto))
//        )).andExpect(status().isOk());
//
//        Posts board = postRepository.findAll().get(1);
//        assertEquals(title, board.getTitle());
//        assertEquals(content, board.getContent());
//    }
////    @Test
////    public void testBoardUpdate() throws Exception {
////        PostRequestDTO dto = PostRequestDTO.builder()
////                .title("수정된 제목")
////                .content("수정된 내용")
////                .build();
////
////
////        Long bno = postRepository.findAll().get(0).getId();
////        String requestUrl = "/api/board/update/"+bno;
////
////        mockMvc.perform((put(requestUrl)
////                .contentType(MediaType.APPLICATION_JSON_UTF8)
////                .content(new ObjectMapper().writeValueAsString(dto))
////        )).andExpect(status().isOk());
////
////        Post board = postRepository.findAll().get(0);
////        assertEquals(dto.getTitle(), board.getTitle());
////        assertEquals(dto.getContent(), board.getContent());
////    }
////    @Test
////    public void testBoardList() throws Exception {
////        mockMvc.perform(get("/api/board/list"))
////                .andExpect(status().isOk())
////                .andDo(print());
////    }
////    @Test
////    public void testBoardDelete() throws Exception {
////        Long bno = postRepository.findAll().get(0).getId();
////        mockMvc.perform(delete("/api/board/delete/"+bno))
////                .andExpect(status().isOk())
////                .andDo(print());
////
////        if(postRepository.findAll().size() > 0){
////            fail("삭제 실패");
////        }
////    }
//}