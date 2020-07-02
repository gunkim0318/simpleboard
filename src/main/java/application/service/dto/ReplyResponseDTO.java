package application.service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ToString
@Getter
public class ReplyResponseDTO {
    private final String content;

    private final String writer;

    private final String creDatetime;

    @Builder
    public ReplyResponseDTO(String content, String writer, LocalDateTime creDatetime){
        this.content = content;
        this.writer = writer;
        this.creDatetime = creDatetime.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 hh시 mm분 ss초"));
    }
}