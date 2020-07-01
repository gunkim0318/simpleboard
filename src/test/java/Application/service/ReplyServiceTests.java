package Application.service;

import application.jpa.domain.Member;
import application.jpa.domain.Posts;
import application.jpa.domain.Reply;
import application.jpa.enums.Gender;
import application.jpa.enums.Role;
import application.jpa.repository.MemberRepository;
import application.jpa.repository.PostsRepository;
import application.jpa.repository.ReplyRepository;
import application.service.ReplyService;
import application.service.dto.ReplyResponseDTO;
import application.web.dto.ReplyRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.IntStream;

@RunWith(SpringRunner.class)
@Slf4j
@SpringBootTest
public class ReplyServiceTests {
    @Autowired
    private ReplyService replyService;
    @Autowired
    private PostsRepository postsRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ReplyRepository replyRepository;

    @Before
    public void before(){
        replyRepository.deleteAll();
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

        Posts posts = Posts.builder()
                .title("제목 테스트")
                .content("내용 테스트")
                .member(member)
                .build();
        postsRepository.save(posts);
    }
    @Test
    public void testWriteReply(){
        ReplyRequestDTO requestDTO = ReplyRequestDTO.builder()
                .content("댓글 테스트")
                .postsId(postsRepository.findAll().get(0).getId())
                .build();

        replyService.writeReply(requestDTO, "gunkim0318@gmail.com");
    }
    @Test
    public void testGetReplyList(){
        Member member = memberRepository.findAll().get(0);
        Posts posts = postsRepository.findAll().get(0);
        IntStream.rangeClosed(1, 100).forEach(i ->{
            Reply reply = Reply.builder()
                    .content(i+"번째 댓글입니더.")
                    .member(member)
                    .posts(posts)
                    .build();

            replyRepository.save(reply);
        });
        long postsId = postsRepository.findAll().get(0).getId();
        List<ReplyResponseDTO> replyList =  replyService.getReplyList(postsId);

        Assert.assertEquals(replyList.get(0).getContent(), "1번째 댓글입니더.");
        Assert.assertEquals(replyList.get(replyList.size()-1).getContent(), "100번째 댓글입니더.");

    }
}