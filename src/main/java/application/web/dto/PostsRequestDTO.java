package application.web.dto;

import application.jpa.domain.Member;
import application.jpa.domain.Posts;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 게시글 Service 요청을 위한 DTO
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PostsRequestDTO {
    private Long id;
    @NotBlank
    @Size(min=1, max=20)
    private String title;
    @NotBlank
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