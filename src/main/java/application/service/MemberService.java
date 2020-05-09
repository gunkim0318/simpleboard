package application.service;

import application.domain.MemberRepository;
import application.dto.request.MemberRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
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