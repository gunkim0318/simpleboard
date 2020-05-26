package Application.service;

import application.domain.*;
import application.dto.PageDTO;
import application.dto.request.PostsRequestDTO;
import application.dto.response.PostsResponseDTO;
import application.service.PostsService;
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
public class PostsServiceTests {
    @Autowired
    private PostsService postsService;

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
                .nickname("관리자")
                .gender(Gender.M)
                .role(Role.ADMIN)
                .build();
        memberRepository.save(member);

        Posts board = Posts.builder()
                .title("제목 테스트")
                .content("내용 테스트")
                .member(member)
                .build();

        postRepository.save(board);
    }

    @Test
    public void testBoardInsert(){
        PostsRequestDTO dto = PostsRequestDTO.builder()
                .title("제목 테스트")
                .content("내용 테스트")
                .build();

        String email = "gunkim0318@gmail.com";
        postsService.insertPost(dto, email);

        Posts board = postRepository.findAll().get(0);
        log.info(board.toString());
        assertEquals(board.getTitle(), dto.getTitle());
        assertEquals(board.getContent(), dto.getContent());
        assertEquals(board.getMember().getEmail(), dto.getMember().getEmail());
    }
    @Test
    public void testBoardUpdate(){
        PostsRequestDTO updateDto = PostsRequestDTO.builder()
                .title("수정된 제목")
                .content("수정된 내용")
                .build();

        PageDTO pagingUtil = new PageDTO();
        pagingUtil.setPageNum(1);

        Long updateId = postsService.selectPostList(pagingUtil).get(0).getId();
        String email = "gunkim0318@gmail.com";

        postsService.updatePost(updateId, updateDto, email);


        Posts board = postRepository.findAll().get(0);
        assertEquals(updateDto.getTitle(), board.getTitle());
        assertEquals(updateDto.getContent(), board.getContent());
    }
    @Test
    public void testBoardListSelect(){
        String title = "제목 테스트";
        String content = "내용 테스트";

        PageDTO pagingUtil = new PageDTO();
        pagingUtil.setPageNum(1);

        PostsResponseDTO responseDTO = postsService.selectPostList(pagingUtil).get(0);

        assertEquals(responseDTO.getTitle(), title);
        assertEquals(responseDTO.getContent(), content);
    }
    @Test
    public void testBoardDelete(){
        PageDTO pagingUtil = new PageDTO();
        pagingUtil.setPageNum(1);

        PostsResponseDTO responseDTO = postsService.selectPostList(pagingUtil).get(0);

        String email = "gunkim0318@gmail.com";
        postsService.deletePost(responseDTO.getId(), email);

        if(postRepository.findAll().size() > 0){
            fail("삭제 실패");
        }
    }
}