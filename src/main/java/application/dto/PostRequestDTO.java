package application.dto;

import application.domain.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@ToString
public class PostRequestDTO {
    private String title;
    private String content;
    private String writer;

    @Builder
    public PostRequestDTO(String title, String content, String writer){
        this.title = title;
        this.content = content;
        this.writer = writer;
    }

    public Post toEntity(){
        return Post.builder()
                .title(this.title)
                .content(this.content)
                .writer(this.writer)
                .build();
    }
}