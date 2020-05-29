package application.web.dto;

import application.jpa.domain.Member;
import application.jpa.domain.Posts;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class PostsRequestDTO {
    private Long id;
    private String title;
    private String content;
    private Member member;

    @Builder
    public PostsRequestDTO(String title, String content, Member member){
        this.title = title;
        this.content = content;
        this.member = member;
    }

    public Posts toEntity(){
        return Posts.builder()
                .title(this.title)
                .content(this.content)
                .member(this.member)
                .build();
    }
}