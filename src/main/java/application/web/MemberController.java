package application.web;

import application.service.MemberService;
import application.util.ErrorsTransUtil;
import application.validator.PasswordCheckValidator;
import application.web.dto.MemberRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 회원 Controller
 */
@Slf4j
@RequiredArgsConstructor
@Controller
public class MemberController {
    private final MemberService memberService;
    private final PasswordCheckValidator passwordCheckValidator;

    /**
     * 커스텀 유효성 검증을 위해 추가
     * @param binder
     */
    @InitBinder
    public void validatorBinder(WebDataBinder binder){
        binder.addValidators(passwordCheckValidator);
    }

    /**
     * 회원가입 페이지 이동
     */
    @GetMapping("/member/signUp")
    public void signUp(){}

    /**
     * 회원가입 처리
     * @param dto
     * @return
     */
    @PostMapping("/member/signUp")
    public String signUp(@Validated MemberRequestDTO dto, BindingResult errors, Model model, RedirectAttributes rttr) {
        if(errors.hasErrors()){
            ErrorsTransUtil errorUtil = new ErrorsTransUtil(errors);
            boolean isFemale = dto.getGender().equals("F");
            model.addAttribute("member", dto);
            model.addAttribute("isFemale", isFemale);
            model.addAttribute("errors", errorUtil.getMap());
            return "/member/signUp";
        }
        rttr.addFlashAttribute("msg", "회원가입에 성공했습니다!");
        memberService.signUp(dto);
        return "redirect:/";
    }
    /**
     * 로그인 페이지 이동
     */
    @GetMapping("/member/signIn")
    public void signIn(){}
}