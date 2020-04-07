package application.domain;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@ToString
public class Board extends TimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String writer;

    @Column(nullable = false)
    @ColumnDefault("0")
    private Long hit;

    @Builder
    public Board(String title, String content, String writer, Long hit){
        this.title = title;
        this.content = content;
        this.writer = writer;
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