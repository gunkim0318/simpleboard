package Application.service;

import application.domain.*;
import application.dto.PostRequestDTO;
import application.dto.PostResponseDTO;
import application.service.PostService;
import application.dto.PageDTO;
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
public class PostServiceTests {
    @Autowired
    private PostService postService;

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
                .nickname("관리자")
                .gender(Gender.M)
                .role(Role.ADMIN)
                .build();
        memberRepository.save(member);

        Post board = Post.builder()
                .title("제목 테스트")
                .content("내용 테스트")
                .member(member)
                .build();

        postRepository.save(board);
    }

//    @Test
//    public void testBoardInsert(){
//        PostRequestDTO dto = PostRequestDTO.builder()
//                .title("제목 테스트")
//                .content("내용 테스트")
//                .writer("안녕")
//                .build();
//
//        postService.insertPost(dto);
//
//        Post board = postRepository.findAll().get(1);
//        assertEquals(board.getTitle(), dto.getTitle());
//        assertEquals(board.getContent(), dto.getContent());
//        assertEquals(board.getWriter(), dto.getWriter());
//    }
//    @Test
//    public void testBoardUpdate(){
//        PostRequestDTO updateDto = PostRequestDTO.builder()
//                .title("수정된 제목")
//                .content("수정된 내용")
//                .build();
//
//        PageDTO pagingUtil = new PageDTO();
//        pagingUtil.setPageNum(1);
//
//        Long updateId = postService.selectPostList(pagingUtil).get(0).getId();
//        postService.updatePost(updateId, updateDto);
//
//
//        Post board = postRepository.findAll().get(0);
//        assertEquals(updateDto.getTitle(), board.getTitle());
//        assertEquals(updateDto.getContent(), board.getContent());
//    }
//    @Test
//    public void testBoardListSelect(){
//        String title = "제목 테스트";
//        String content = "내용 테스트";
//
//        PageDTO pagingUtil = new PageDTO();
//        pagingUtil.setPageNum(1);
//
//        PostResponseDTO responseDTO = postService.selectPostList(pagingUtil).get(0);
//
//        assertEquals(responseDTO.getTitle(), title);
//        assertEquals(responseDTO.getContent(), content);
//    }
//    @Test
//    public void testBoardDelete(){
//        PageDTO pagingUtil = new PageDTO();
//        pagingUtil.setPageNum(1);
//
//        PostResponseDTO responseDTO = postService.selectPostList(pagingUtil).get(0);
//
//        postService.deletePost(responseDTO.getId());
//
//        if(postRepository.findAll().size() > 0){
//            fail("삭제 실패");
//        }
//    }
}