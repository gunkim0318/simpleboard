package application.web;

import application.dto.PostResponseDTO;
import application.service.PostService;
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
    private final PostService boardService;

    @GetMapping("/")
    public String index(@ModelAttribute PagingUtil pagingUtil, Model model){
        List<PostResponseDTO> boardList = boardService.selectBoardList(pagingUtil);
        model.addAttribute("list", boardList);
        return "/board/list";
    }
}