package application.config;

import application.domain.Member;
import application.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final MemberRepository memberRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email);

        if(member == null){
            throw new UsernameNotFoundException("해당 "+email+"(email)의 유저가 없습니다.");
        }

        Collection<SimpleGrantedAuthority> roles = new ArrayList<SimpleGrantedAuthority>();

        roles.add(new SimpleGrantedAuthority(member.getRole().getValue()));

        return new User(email, member.getPassword(), roles);
    }
}