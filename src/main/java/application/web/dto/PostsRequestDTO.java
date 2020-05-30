package application.web.dto;

import application.jpa.domain.Member;
import application.jpa.domain.Posts;
import lombok.*;

import javax.validation.constraints.NotEmpty;

/**
 * 게시글 Service 요청을 위한 DTO
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PostsRequestDTO {
    private Long id;
    @NotEmpty
    private String title;
    @NotEmpty
    private String content;

    private Member member;

    @Builder
    public PostsRequestDTO(Long id, String title, String content, Member member){
        this.id = id;
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