package application.web;

import application.dto.PostResponseDTO;
import application.service.PostService;
import application.dto.PageDTO;
import application.util.PagingUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class PostController {
    private final PostService postService;

    @GetMapping("/")
    public String index(@ModelAttribute PageDTO pageDTO, Model model){
        List<PostResponseDTO> boardList = postService.selectPostList(pageDTO);
        model.addAttribute("list", boardList);
        model.addAttribute("pagingUtil", new PagingUtil(pageDTO, postService.selectTotalPostCnt()));
        return "/post/list";
    }
    @GetMapping("/post/add")
    public void add(){
    }

    @GetMapping("/post/get")
    public void get(Model model){
//        model.addAttribute("content", postService.selectBoardContent(id));
    }
}