package application.web;

import application.service.PostsService;
import application.service.dto.PostsResponseDTO;
import application.util.PagingUtil;
import application.web.dto.PageRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

/**
 * 인덱스 페이지 컨트롤러
 */
@Slf4j
@RequiredArgsConstructor
@Controller
public class IndexController {
    private final PostsService postsService;
    private static final String INDEX_PATH = "/posts/list";

    /**
     * 인덱스 페이지 호출
     * @param pageRequestDTO
     * @param model
     * @return
     */
    @GetMapping("/")
    public String index(@ModelAttribute PageRequestDTO pageRequestDTO, Model model){
        log.info("page DTO : "+pageRequestDTO);
        List<PostsResponseDTO> boardList = postsService.selectPostsList(pageRequestDTO);
        model.addAttribute("list", boardList);
        model.addAttribute("pagingUtil", new PagingUtil(pageRequestDTO, postsService.selectTotalPostCnt()));

        return INDEX_PATH;
    }
}
