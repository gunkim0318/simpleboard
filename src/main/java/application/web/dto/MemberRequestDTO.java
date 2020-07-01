package application.web.dto;

import application.jpa.enums.Gender;
import application.jpa.domain.Member;
import application.jpa.enums.Role;
import lombok.*;

import javax.validation.constraints.*;

/**
 * 회원 Service 요청을 위한 DTO
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class MemberRequestDTO {
    @Email
    @NotBlank
    private String email;
    @NotBlank
    @Pattern(regexp="([a-zA-Z0-9].*[!,@,#,$,%,^,&,*,?,_,~])|([!,@,#,$,%,^,&,*,?,_,~].*[a-zA-Z0-9]){8,12}" ,message="숫자 영문자 특수 문자를 포함한 8 ~ 12 자를 입력하세요. ")
    private String password;
    @NotBlank
    @Pattern(regexp="([a-zA-Z0-9].*[!,@,#,$,%,^,&,*,?,_,~])|([!,@,#,$,%,^,&,*,?,_,~].*[a-zA-Z0-9]){8,12}" ,message="숫자 영문자 특수 문자를 포함한 8 ~ 12 자를 입력하세요. ")
    private String passwordChk;
    @Size(min=2, max=10)
    @NotBlank
    private String nickname;
    @NotBlank
    private String gender;

    @Builder
    public MemberRequestDTO(String email, String password, String passwordChk, String nickname, String gender){
        this.email = email;
        this.password = password;
        this.passwordChk = passwordChk;
        this.nickname = nickname;
        this.gender = gender;
    }
    public boolean isPwEqualToCheckPw(){
        return password.equals(passwordChk);
    }

    public Member toEntity(){
        return Member.builder()
                .email(this.email)
                .password(this.password)
                .nickname(this.nickname)
                .gender(Gender.valueOf(gender))
                .role(Role.USER)
                .build();
    }
}