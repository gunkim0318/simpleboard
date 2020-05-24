package application.dto.request;

import application.domain.Member;
import application.domain.Post;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class PostRequestDTO {
    private Long id;
    private String title;
    private String content;
    private Member member;

    @Builder
    public PostRequestDTO(String title, String content, Member member){
        this.title = title;
        this.content = content;
        this.member = member;
    }

    public Post toEntity(){
        return Post.builder()
                .title(this.title)
                .content(this.content)
                .member(this.member)
                .build();
    }
}