package application.jpa.domain;

import application.jpa.domain.common.TimeEntity;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.List;

/**
 * 게시글 도메인
 * 하나의 게시글은 하나의 멤버만 가질 수 있다.
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@ToString
public class Posts extends TimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String title;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name="member_id")
    private Member member;

    @Column(nullable = false)
    @ColumnDefault("0")
    private Long hit;

    @OneToMany(mappedBy = "posts", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reply> reply;

    @Builder
    public Posts(String title, String content, Member member, Long hit){
        this.title = title;
        this.content = content;
        this.member = member;
        this.hit = hit;
    }
    @PrePersist
    public void prePersist() {
        if(hit == null){
            this.hit = 0l;
        }
    }
    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }
    public void hitUp(){
        this.hit += 1;
    }
}