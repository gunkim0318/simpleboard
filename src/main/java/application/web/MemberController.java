package application.web;

import application.web.dto.MemberRequestDTO;
import application.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/member/signUp")
    public void signUp(){
    }
    @PostMapping("/member/signUp")
    public String signUp(MemberRequestDTO dto) {
        log.info(dto.toString());
        memberService.signUp(dto);

        return "redirect:/";
    }
    @GetMapping("/member/signIn")
    public void signIn(){
    }
}