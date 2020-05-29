package application.service.dto;

import lombok.*;

/**
 * 회원 Service 응답을 위한 DTO
 */
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