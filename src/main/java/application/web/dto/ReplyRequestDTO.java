package application.web.dto;

import application.jpa.domain.Member;
import application.jpa.domain.Posts;
import application.jpa.domain.Reply;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ReplyRequestDTO {
    private String content;
    private long postsId;

    @Builder
    public ReplyRequestDTO(String content, long postsId){
        this.content = content;
        this.postsId = postsId;
    }
    public Reply toEntity(Member member, Posts posts){
        return Reply.builder()
                .content(this.content)
                .member(member)
                .posts(posts)
                .build();
    }
}