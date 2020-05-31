package application.service.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReplyResponseDTO {
    private final String content;

    private final String writer;

    private final LocalDateTime creDatetime;

    @Builder
    public ReplyResponseDTO(String content, String writer, LocalDateTime creDatetime){
        this.content = content;
        this.writer = writer;
        this.creDatetime = creDatetime;
    }
}