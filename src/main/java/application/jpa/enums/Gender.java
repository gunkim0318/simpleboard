package application.jpa.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 성별 데이터 enum
 */
@AllArgsConstructor
@Getter
public enum Gender {
    M("남성"),
    F("여성");

    private String title;
}
