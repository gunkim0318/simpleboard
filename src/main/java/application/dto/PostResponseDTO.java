package application.dto;

import application.domain.Post;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PostResponseDTO {
    private Long id;
    private String title;
    private String content;
    private String writer;
    private Long hit;

    public PostResponseDTO(Post board){
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.writer = board.getWriter();
        this.hit = board.getHit();
    }
}