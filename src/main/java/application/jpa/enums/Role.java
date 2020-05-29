package application.jpa.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 권한 데이터 enum
 */
@AllArgsConstructor
@Getter
public enum Role {
    ADMIN("관리자", "ROLE_ADMIN"),
    USER("사용자", "ROLE_USER");

    private String title;
    private String value;
}