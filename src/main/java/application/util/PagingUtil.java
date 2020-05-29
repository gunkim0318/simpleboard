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
 * 페이징 처리를 위한 유틸
 */
@ToString
public class PagingUtil {
    private int startPageNum, endPageNum;
    @Getter
    private boolean isPrev, isNext;
    @Getter
    private int prevPageNum, nextPageNum;

    private int totalPostNum, totalPageNum;

    private PageRequestDTO pageRequestDTO;

    private int viewPageNum = 5;
    @Getter
    private List<Map<String, String>> pagingList;

    public PagingUtil(PageRequestDTO pageRequestDTO, int totalPostNum){
        this.pageRequestDTO = pageRequestDTO;
        this.totalPostNum = totalPostNum;

        this.endPageNum =  (int)(Math.ceil(pageRequestDTO.getPageNum() / (double) this.viewPageNum)) * this.viewPageNum;
        this.startPageNum = this.endPageNum - (this.viewPageNum - 1);

        this.totalPageNum = (int) (Math.ceil( (totalPostNum * 1.0) / pageRequestDTO.getViewPostCnt() ) );

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