package application.jpa.domain;

import application.jpa.domain.common.TimeEntity;
import application.jpa.enums.Gender;
import application.jpa.enums.Role;
import lombok.*;

import javax.persistence.*;

/**
 * 회원 도메인
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Getter
@Entity
public class Member extends TimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 20)
    private String nickname;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public Member(Long id, String email, String password, String nickname, Gender gender, Role role){
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.gender = gender;
        this.role = role;
    }

    /**
     * 패스워드를 변경함
     * @param password
     */
    public void updatePassword(String password){
        this.password = password;
    }

    /**
     * 닉네임을 변경함
     * @param nickname
     */
    public void updateNickname(String nickname){
        this.nickname = nickname;
    }

    /**
     * 권한을 변경함
     * @param role
     */
    public void updateRole(Role role){
        this.role = role;
    }
}