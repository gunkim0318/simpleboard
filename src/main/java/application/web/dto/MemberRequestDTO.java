package application.web.dto;

import application.jpa.enums.Gender;
import application.jpa.domain.Member;
import application.jpa.enums.Role;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

/**
 * 회원 Service 요청을 위한 DTO
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class MemberRequestDTO {
    @Email
    @NotEmpty
    private String email;
    @NotEmpty
    private String password;
    @NotEmpty
    private String passwordChk;
    @NotEmpty
    private String nickname;
    @NotEmpty
    private String gender;

    @Builder
    public MemberRequestDTO(String email, String password, String passwordChk, String nickname, String gender){
        this.email = email;
        this.password = password;
        this.passwordChk = passwordChk;
        this.nickname = nickname;
        this.gender = gender;
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