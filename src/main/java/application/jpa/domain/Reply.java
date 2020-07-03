package application.jpa.domain;

import application.jpa.domain.common.TimeEntity;
import lombok.*;

import javax.persistence.*;

/**
 * 댓글 도메인
 */
@ToString
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

    @ManyToOne
    @JoinColumn(nullable = false, name = "posts_id")
    private Posts posts;

    @Builder
    public Reply(String content, Member member, Posts posts){
        this.content = content;
        this.member = member;
        this.posts = posts;
    }
    public void update(String content){
        this.content = content;
    }
}