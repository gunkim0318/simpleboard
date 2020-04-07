package application.web;

import application.dto.PostResponseDTO;
import application.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/board")
@Controller
public class PostController {
    private final PostService boardService;

    @GetMapping("/list")
    public String list(Model model){
        List<PostResponseDTO> boardList = boardService.selectBoardList();
        model.addAttribute("list", boardList);
        return "/board/list";
    }
}