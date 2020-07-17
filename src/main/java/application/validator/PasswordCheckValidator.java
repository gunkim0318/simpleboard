package application.validator;

import application.web.dto.MemberRequestDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

/**
 * 비밀번호 확인 유효성 검증을 위한 커스텀 Validator클래스
 */
@Component
public class PasswordCheckValidator extends AbstractValidator<MemberRequestDTO> {
    @Override
    protected void doValidate(MemberRequestDTO form, Errors errors) {
        if(form.isPwNotEquals()){
            errors.rejectValue("passwordChk", "확인 비밀번호 불일치 오류", "비밀번호와 확인 비밀번호가 일치하지 않습니다.");
        }
    }
}
