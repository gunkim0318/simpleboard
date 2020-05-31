package application.jpa.domain;

import application.jpa.domain.common.TimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * 댓글 도메인
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Reply extends TimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @OneToOne
    @JoinColumn(nullable = false, name = "member_id")
    private Member member;

    @Builder
    public Reply(String content, Member member){
        this.content = content;
        this.member = member;
    }
    public void update(String content){
        this.content = content;
    }
}