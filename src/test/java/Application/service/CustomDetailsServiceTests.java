package Application.service;

import application.jpa.domain.Member;
import application.jpa.enums.Gender;
import application.jpa.enums.Role;
import application.jpa.repository.MemberRepository;
import application.service.CustomUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomDetailsServiceTests {
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Before
    public void before(){
        Member member = Member.builder()
                .email("gunkim0318@gmail.com")
                .gender(Gender.M)
                .nickname("건맴")
                .password(passwordEncoder.encode("test1234"))
                .role(Role.USER)
                .build();

        memberRepository.save(member);
    }
    @Test
    public void testLoadUserByUsername(){
        String email = "gunkim0318@gmail.com";
        User user = (User) customUserDetailsService.loadUserByUsername(email);

        Assert.assertEquals(email, user.getUsername());
    }
}