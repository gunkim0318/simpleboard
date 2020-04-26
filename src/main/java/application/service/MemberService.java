package application.service;

import application.domain.MemberRepository;
import application.dto.MemberRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public void signUp(MemberRequestDTO dto){
        memberRepository.save(dto.toEntity());
    }
}