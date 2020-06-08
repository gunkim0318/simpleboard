package application.service.dto;

import application.jpa.domain.Posts;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 게시글 Service 응답을 위한 DTO
 */
@Getter
@ToString
public class PostsResponseDTO {
    private Long id;
    private String title;
    private String content;
    private String writer;
    private String email;
    private String createDate;
    private Long hit;

    public PostsResponseDTO(Posts posts){
        this.id = posts.getId();
        this.title = posts.getTitle();
        this.content = posts.getContent();
        this.writer = posts.getMember().getNickname();
        this.email = posts.getMember().getEmail();
        this.hit = posts.getHit();
        this.setCreateDate(posts.getCreateDate());
    }
    private void setCreateDate(LocalDateTime createDate){
        String dateFormatStr = "yyyy-MM-dd HH:mm:ss";
        this.createDate = createDate.format(DateTimeFormatter.ofPattern(dateFormatStr));
    }
}