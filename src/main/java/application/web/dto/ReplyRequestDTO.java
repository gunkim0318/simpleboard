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
    private long postsId;
    private long replyId;
    private String content;

    @Builder
    public ReplyRequestDTO(long postsId, long replyId, String content){
        this.postsId = postsId;
        this.replyId = replyId;
        this.content = content;
    }
    public Reply toEntity(Member member, Posts posts){
        return Reply.builder()
                .content(this.content)
                .member(member)
                .posts(posts)
                .build();
    }
}