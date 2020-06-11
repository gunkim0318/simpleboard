package application.util;

import application.web.dto.PageRequestDTO;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * 화면에서 표시하기 위한 페이징 유틸
 */
@ToString
public class PagingUtil {
    private int startPageNum, endPageNum;
    @Getter
    private final boolean isPrev, isNext;
    @Getter
    private final int prevPageNum, nextPageNum;

    private final int totalPostsNum, totalPageNum;

    private final PageRequestDTO pageRequestDTO;

    private final int viewPageNum;

    @Getter
    private List<Map<String, String>> pagingList;

    public PagingUtil(PageRequestDTO pageRequestDTO, int totalPostsNum){
        this.viewPageNum = 5;
        this.pageRequestDTO = pageRequestDTO;
        this.totalPostsNum = totalPostsNum;

        this.endPageNum =  (int)(Math.ceil(pageRequestDTO.getPageNum() / (double) this.viewPageNum)) * this.viewPageNum;
        this.startPageNum = this.endPageNum - (this.viewPageNum - 1);

        this.totalPageNum = (int) (Math.ceil( (totalPostsNum * 1.0) / pageRequestDTO.getViewPostsCnt() ) );

        if(this.totalPageNum < this.endPageNum){
            this.endPageNum = this.totalPageNum;
        }

        this.isPrev = this.startPageNum > this.viewPageNum;
        this.isNext = this.endPageNum < totalPageNum;

        pagingList = new ArrayList<Map<String, String>>();

        IntStream.rangeClosed(startPageNum, endPageNum).forEach(i -> {
            Map<String, String> map = new HashMap<String, String>();
            map.put("idx", String.valueOf(i));

            String active = "";
            if(i == pageRequestDTO.getPageNum()){
                active = "active";
            }
            map.put("active", active);
            pagingList.add(map);
        });
        this.prevPageNum = this.startPageNum - 1;
        this.nextPageNum = this.endPageNum + 1;
    }
}