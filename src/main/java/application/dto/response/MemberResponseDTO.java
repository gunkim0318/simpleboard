package application.dto.response;

import lombok.*;
@Getter
@ToString
public class MemberResponseDTO {
    private String email;
    private String password;
    private String nickname;
    private String gender;

    @Builder
    public MemberResponseDTO(String email, String password, String nickname, String gender){
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.gender = gender;
    }
}