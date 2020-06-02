package Application.web;

import application.jpa.domain.Member;
import application.jpa.domain.Posts;
import application.jpa.enums.Gender;
import application.jpa.enums.Role;
import application.jpa.repository.MemberRepository;
import application.jpa.repository.PostsRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class PostsControllerTests {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private PostsRepository postRepository;
    @Autowired
    private MemberRepository memberRepository;

    @Before
    public void before() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();

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
    public void testIndex() throws Exception{
        mockMvc.perform(get("/")).andExpect(status().isOk());
    }
    @Test
    public void testGet() throws Exception{
        long postsId = postRepository.findAll().get(0).getId();
        mockMvc.perform(get("/posts/get").param("postsNum", String.valueOf(postsId)))
                .andExpect(status().isOk());
    }
    @Test
    @WithMockUser(roles = "USER", username = "gunkim0318@gmail.com")
    public void testInsert() throws Exception{
        String title = "안녕";
        String content = "안녕 내용";
        mockMvc.perform(post("/posts/insert").param("title", title).param("content", content).with(csrf()))
                .andExpect(status().is3xxRedirection());

        Posts dto = postRepository.findAll().get(1);
        Assert.assertEquals(dto.getTitle(), title);
        Assert.assertEquals(dto.getContent(), content);
    }
    @Test
    @WithMockUser(roles = "USER", username = "gunkim0318@gmail.com")
    public void testUpdate() throws Exception{

    }
}