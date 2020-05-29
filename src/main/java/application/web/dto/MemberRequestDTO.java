package application.web.dto;

import application.jpa.enums.Gender;
import application.jpa.domain.Member;
import application.jpa.enums.Role;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MemberRequestDTO {
    private String email;
    private String password;
    private String nickname;
    private String gender;

    @Builder
    public MemberRequestDTO(String email, String password, String nickname, String gender){
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
                .gender(Gender.valueOf(gender))
                .role(Role.USER)
                .build();
    }
}