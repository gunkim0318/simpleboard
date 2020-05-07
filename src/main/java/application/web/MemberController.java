package application.web;

import application.config.CustomUserDetailService;
import application.dto.MemberRequestDTO;
import application.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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