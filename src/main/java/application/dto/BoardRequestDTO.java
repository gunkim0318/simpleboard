package application.dto;

import application.domain.Board;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class BoardRequestDTO {
    private String title;
    private String content;
    private String writer;

    @Builder
    public BoardRequestDTO(String title, String content, String writer){
        this.title = title;
        this.content = content;
        this.writer = writer;
    }

    public Board toEntity(){
        return Board.builder()
                .title(this.title)
                .content(this.content)
                .writer(this.writer)
                .build();
    }
}