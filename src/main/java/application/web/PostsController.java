package application.web;

import application.service.PostsService;
import application.service.ReplyService;
import application.service.dto.PostsResponseDTO;
import application.web.dto.PostsRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * 게시글 Controller
 */
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/posts")
@Controller
public class PostsController {
    private final PostsService postsService;
    private final ReplyService replyService;

    @GetMapping("/add")
    public void add(){}

    @GetMapping("/get")
    public void get(@RequestParam("postsId") Long postsId, Model model, Principal principal){
        PostsResponseDTO dto = postsService.selectBoardContent(postsId);
        Integer replyCnt = replyService.getReplyCnt(postsId);

        model.addAttribute("posts", dto);
        model.addAttribute("replyCnt", replyCnt);

        if(principal != null){
            String email = principal.getName();
            model.addAttribute("isMyPosts", email.equals(dto.getWriter()));
        }
    }
    @GetMapping("/modify")
    public void modify(@RequestParam("postsNum") Long id, Model model){
        model.addAttribute("posts", postsService.selectBoardContent(id));
    }
    /**
     * 게시글 작성
     * @param dto
     */
    @PostMapping("/insert")
    public String insert(@ModelAttribute PostsRequestDTO dto, Principal principal){
        String email = principal.getName();
        postsService.insertPosts(dto, email);

        return "redirect:/";
    }

    /**
     * 게시글 수정
     * @param dto
     * @param principal
     */
    @PostMapping("/update")
    public String update(@ModelAttribute PostsRequestDTO dto, Principal principal){
        String email = principal.getName();
        postsService.updatePosts(dto, email);

        return "redirect:/";
    }

    /**
     * 게시글 삭제
     * @param id
     */
    @PostMapping("/delete")
    public String delete(@RequestParam("postsNum") Long id, Principal principal){
        String email = principal.getName();
        postsService.deletePosts(id, email);

        return "redirect:/";
    }
}