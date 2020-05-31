package Application.domain;

import application.jpa.domain.Member;
import application.jpa.domain.Reply;
import application.jpa.enums.Gender;
import application.jpa.enums.Role;
import application.jpa.repository.MemberRepository;
import application.jpa.repository.ReplyRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@DataJpaTest
public class ReplyRepositoryTests {
    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Before
    public void before(){
        memberRepository.deleteAll();
        Member member = Member.builder()
                .email("gunkim0318@gmail.com")
                .password("test")
                .nickname("테스트")
                .gender(Gender.M)
                .role(Role.USER)
                .build();
        memberRepository.save(member);

        Reply reply = Reply.builder()
                .content("첫 번째 댓글입니더.")
                .member(member)
                .build();

        replyRepository.save(reply);
    }
    @Test
    public void testReplyUpdate(){
        String content = "댓글 수정합니더.";
        Reply reply = replyRepository.findAll().get(0);
        reply.update("댓글 수정합니더.");

        replyRepository.save(reply);

        Reply saveReply = replyRepository.findAll().get(0);
        Assert.assertEquals(content, saveReply.getContent());
    }
    @Test
    public void testReplyDelete(){
        Reply reply = replyRepository.findAll().get(0);

        replyRepository.delete(reply);
    }
}