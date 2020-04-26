package application.dto;

import application.domain.Gender;
import application.domain.Member;
import application.domain.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class MemberRequestDTO {
    private String email;
    private String password;
    private String nickname;
    private Gender gender;

    @Builder
    public MemberRequestDTO(String email, String password, String nickname, Gender gender){
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.gender = gender;
    }
    public Member toEntity(){
        return Member.builder()
                .email(this.email)
                .password(this.password)
                .nickname(this.nickname)
                .gender(this.gender)
                .role(Role.USER)
                .build();
    }
}