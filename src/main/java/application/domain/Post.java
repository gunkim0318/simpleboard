package application.domain;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@ToString
public class Post extends TimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @OneToOne
    @JoinColumn(nullable = false, name="member_id")
    private Member member;

    @Column(nullable = false)
    @ColumnDefault("0")
    private Long hit;

    @Builder
    public Post(String title, String content, Member member, Long hit){
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