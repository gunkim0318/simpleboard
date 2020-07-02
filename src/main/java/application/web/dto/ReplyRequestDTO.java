package application.web.dto;

import application.jpa.domain.Member;
import application.jpa.domain.Posts;
import application.jpa.domain.Reply;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NegativeOrZero;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;

@NoArgsConstructor
@Data
public class ReplyRequestDTO {
    @Null(message = "잘못된 접근인 것 같습니다.")
    @NegativeOrZero(message = "잘못된 접근인 것 같습니다.")
    private long postsId;
    @Null(message = "잘못된 접근인 것 같습니다.")
    @NegativeOrZero(message = "잘못된 접근인 것 같습니다.")
    private long replyId;
    @NotBlank
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