package Application.service;

import application.jpa.domain.Member;
import application.jpa.repository.MemberRepository;
import application.service.MemberService;
import application.web.dto.MemberRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberServiceTests {
    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Before
    public void before(){
        memberRepository.deleteAll();
    }

    @Test
    public void testSignUp(){
        MemberRequestDTO dto = MemberRequestDTO.builder()
                .email("gunkim0318@gmail.com")
                .password("rlarjs123")
                .nickname("슈퍼맨")
                .gender("M")
                .build();

        memberService.signUp(dto);

        Member findMember = memberRepository.findAll().get(0);

        assertEquals(dto.getEmail(), findMember.getEmail());
        assertEquals(dto.getPassword(), findMember.getPassword());
        assertEquals(dto.getNickname(), findMember.getNickname());
        assertEquals(dto.getGender(), findMember.getGender().name());
    }
}