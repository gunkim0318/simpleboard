package application.service;

import application.jpa.repository.MemberRepository;
import application.web.dto.MemberRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 회원 Service
 */
@Slf4j
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    public void signUp(MemberRequestDTO dto){
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        memberRepository.save(dto.toEntity());
    }
}