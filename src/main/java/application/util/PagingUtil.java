package application.util;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;

/**
 * 페이징을 위한 유틸
 */
@ToString
@NoArgsConstructor
@Slf4j
@Getter
public class PagingUtil {
    //화면에 보여질 게시글 수
    //보여줄 갯수는 10개 일단 고정...(변경 가능)
    @Setter
    private int viewPostCnt = 10;
    //보고자 하는 페이지 번호
    private int pageNum = 1;

    private int totalPageNum;
    /**
     * 실제 페이지 번호는 0부터 시작되기 때문에 실제 반영 시 -1을 해줌
     * @return
     */
    public int getPageNum(){
        return (this.pageNum - 1);
    }

    /**
     * 페이지 번호가 1부터 시작하기 때문에 1보다 작으면 1로 할당
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
        return PageRequest.of(this.getPageNum(), this.viewPostCnt);
    }
}