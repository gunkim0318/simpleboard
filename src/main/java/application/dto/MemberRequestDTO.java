package application.dto;

import application.domain.Gender;
import application.domain.Member;
import application.domain.Role;
import com.sun.org.glassfish.gmbal.NameValue;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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
                .password(new BCryptPasswordEncoder().encode(this.password))
                .nickname(this.nickname)
                .gender(Gender.valueOf(gender))
                .role(Role.USER)
                .build();
    }
}