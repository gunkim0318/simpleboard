package application.web.dto;

import lombok.*;
import org.springframework.data.domain.PageRequest;

/**
 * 페이징을 위한 유틸..인데 DTO로 분류.  DTO가 맞지 않나..?
 */
@ToString
@Getter
public class PageRequestDTO {
    //보고자 하는 페이지 번호
    @Setter
    private int pageNum;

    //화면에 보여질 게시글 수
    //보여줄 갯수는 10개 일단 고정...
    @Setter(AccessLevel.PRIVATE)
    private int viewPostsCnt = 10;

    public PageRequestDTO(){
        this(1, 10);
    }
    public PageRequestDTO(int pageNum){
        this(pageNum, 10);
    }
    public PageRequestDTO(int pageNum, int viewPostsCnt){
        this.setPageNum(pageNum);
        this.setViewPostsCnt(viewPostsCnt);
    }

    /**
     *
     * @param pageNum
     */
    public void setPageNum(int pageNum){
        if(pageNum < 1){
            this.pageNum = 1;
            return;
        }
        this.pageNum = pageNum;
    }

    /**
     * 실제 페이징을 위한 엔티티 생성 후 변환
     * @return
     */
    public PageRequest toEntity(){
        return PageRequest.of(this.getPageNum() - 1, this.viewPostsCnt);
    }
}