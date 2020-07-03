package Application.domain;

import application.jpa.domain.Member;
import application.jpa.domain.Posts;
import application.jpa.domain.Reply;
import application.jpa.enums.Gender;
import application.jpa.enums.Role;
import application.jpa.repository.MemberRepository;
import application.jpa.repository.PostsRepository;
import application.jpa.repository.ReplyRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.stream.IntStream;

@Slf4j
@RunWith(SpringRunner.class)
@DataJpaTest
public class ReplyRepositoryTests {
    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PostsRepository postsRepository;

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

        Posts posts = Posts.builder()
                .title("테스트 게시글입니다.")
                .content("테스트 내용입니다.")
                .member(member)
                .hit(0l)
                .build();

        postsRepository.save(posts);

        Reply reply = Reply.builder()
                .content("첫 번째 댓글입니더.")
                .member(member)
                .posts(posts)
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
    @Test
    public void testReplyPaging(){
        Member member = memberRepository.findAll().get(0);
        Posts posts = postsRepository.findAll().get(0);

        IntStream.rangeClosed(1, 100).forEach(i -> {
            replyRepository.save(Reply.builder()
                    .content(i+"안녕하십니꺼")
                    .member(member)
                    .posts(posts)
                    .build());
        });
        replyRepository.findAllByPostsOrderByIdDesc(posts, PageRequest.of(1, 10)).stream().forEach(reply -> {
            log.info(reply.toString());
        });
    }
}