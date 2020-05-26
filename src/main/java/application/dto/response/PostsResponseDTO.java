package application.dto.response;

import application.domain.Posts;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@ToString
public class PostsResponseDTO {
    private Long id;
    private String title;
    private String content;
    private String writer;
    private String createDate;
    private Long hit;

    public PostsResponseDTO(Posts posts){
        this.id = posts.getId();
        this.title = posts.getTitle();
        this.content = posts.getContent();
        this.writer = posts.getMember().getNickname();
        this.hit = posts.getHit();
        this.setCreateDate(posts.getCreateDate());
    }
    private void setCreateDate(LocalDateTime createDate){
        String dateFormatStr = "yyyy-MM-dd HH:mm:ss";
        this.createDate = createDate.format(DateTimeFormatter.ofPattern(dateFormatStr));
    }
}