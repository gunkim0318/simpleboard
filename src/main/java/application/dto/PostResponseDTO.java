package application.dto;

import application.domain.Post;
import lombok.Getter;
import lombok.ToString;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@ToString
public class PostResponseDTO {
    private Long id;
    private String title;
    private String content;
    private String writer;
    private String createDate;
    private Long hit;

    public PostResponseDTO(Post post){
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.writer = post.getWriter();
        this.hit = post.getHit();
        this.setCreateDate(post.getCreateDate());
    }
    private void setCreateDate(LocalDateTime createDate){
        String dateFormatStr = "yyyy-MM-dd HH:mm:ss";
        this.createDate = createDate.format(DateTimeFormatter.ofPattern(dateFormatStr));
    }
}