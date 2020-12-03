package Application.domain;

import application.jpa.domain.Member;
import application.jpa.enums.Gender;
import application.jpa.enums.Role;
import application.jpa.repository.MemberRepository;
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
public class MemberRepositoryTests {

    @Autowired
    private MemberRepository memberRepository;

    @Before
    public void before(){
        memberRepository.deleteAll();

        Member member = Member.builder()
                .email("gunkim0318@gmail.com")
                .password("rlarjs123")
                .nickname("관리자")
                .gender(Gender.M)
                .role(Role.ADMIN)
                .build();
        memberRepository.save(member);
    }
    @Test
    public void testInsertMember(){
        String email = "gunkim0318@gmail.com";
        String password = "rlarjs123";

        Member findMember = memberRepository.findAll().get(0);
        assertEquals(email, findMember.getEmail());
        assertEquals(password, findMember.getPassword());
    }
    @Test
    public void testUpdateMember(){
        String password = "test1234";
        String nickname = "nick~";
        Role role = Role.USER;

        Member findMember = memberRepository.findAll().get(0);
        findMember.updateNickname(nickname);
        findMember.updatePassword(password);
        findMember.updateRole(role);

        assertEquals(password, findMember.getPassword());
        assertEquals(nickname, findMember.getNickname());
        assertEquals(role, findMember.getRole());
    }
    @Test
    public void testDeleteMember(){
        Member member = memberRepository.findAll().get(0);

        memberRepository.delete(member);
    }
}