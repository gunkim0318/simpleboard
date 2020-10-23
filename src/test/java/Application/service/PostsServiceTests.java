package Application.service;

import application.jpa.domain.Member;
import application.jpa.domain.Posts;
import application.jpa.enums.Gender;
import application.jpa.enums.Role;
import application.jpa.repository.MemberRepository;
import application.jpa.repository.PostsRepository;
import application.service.PostsService;
import application.service.dto.PostsResponseDTO;
import application.web.dto.PageRequestDTO;
import application.web.dto.PostsRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class PostsServiceTests {
    @Autowired
    private PostsService postsService;
    @Autowired
    private PostsRepository postsRepository;
    @Autowired
    private MemberRepository memberRepository;

    @Before
    public void before(){
        postsRepository.deleteAll();
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

        postsRepository.save(board);
    }

    @Test
    public void testPostsInsert(){
        PostsRequestDTO dto = PostsRequestDTO.builder()
                .title("제목 테스트")
                .content("내용 테스트")
                .build();

        String email = "gunkim0318@gmail.com";
        postsService.insertPosts(dto, email);

        Posts board = postsRepository.findAll().get(0);
        assertEquals(board.getTitle(), dto.getTitle());
        assertEquals(board.getContent(), dto.getContent());
        assertEquals(board.getMember().getEmail(), dto.getMember().getEmail());
    }
    @Test
    public void testPostsUpdate(){
        Long updateId = postsRepository.findAll().get(0).getId();
        String email = "gunkim0318@gmail.com";

        PostsRequestDTO requestDto = PostsRequestDTO.builder()
                .id(updateId)
                .title("수정된 제목")
                .content("수정된 내용")
                .build();

        postsService.updatePosts(requestDto, email);

        Posts board = postsRepository.findAll().get(0);
        assertEquals(requestDto.getTitle(), board.getTitle());
        assertEquals(requestDto.getContent(), board.getContent());
    }
    @Test
    public void testPostsListSelect(){
        String title = "제목 테스트";
        String content = "내용 테스트";

        PageRequestDTO pagingUtil = new PageRequestDTO();
        pagingUtil.setPageNum(1);

        PostsResponseDTO responseDTO = postsService.selectPostsList(pagingUtil).get(0);

        assertEquals(responseDTO.getTitle(), title);
        assertEquals(responseDTO.getContent(), content);
    }
    @Test
    public void testPostsDelete(){
        String email = "gunkim0318@gmail.com";

        long postsId = postsRepository.findAll().get(0).getId();
        postsService.deletePosts(postsId, email);

        if(postsRepository.findAll().size() > 0){
            fail("삭제 실패");
        }
    }
}