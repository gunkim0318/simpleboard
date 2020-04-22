package application.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MemberController {
    @GetMapping("/member/signUp")
    public void signUp(){
    }
    @GetMapping("/member/signIn")
    public void signIn(){
    }
}
