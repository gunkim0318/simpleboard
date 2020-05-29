package application.config;

import application.jpa.enums.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class SessionUser {
    private String username;
    private Role role;

    @Builder
    public SessionUser(String username, Role role){
        this.username = username;
        this.role = role;
    }
}