package application.web;

import application.dto.request.PostRequestDTO;
import application.dto.response.PostResponseDTO;
import application.service.PostService;
import application.dto.PageDTO;
import application.util.PagingUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class PostController {
    private final PostService postsService;

    @GetMapping("/")
    public String index(@ModelAttribute PageDTO pageDTO, Model model){
        List<PostResponseDTO> boardList = postsService.selectPostList(pageDTO);
        model.addAttribute("list", boardList);
        model.addAttribute("pagingUtil", new PagingUtil(pageDTO, postsService.selectTotalPostCnt()));
        return "/posts/list";
    }
    @GetMapping("/posts/add")
    public void add(){
    }

    @GetMapping("/posts/get")
    public void get(@RequestParam("postsNum") Long id, Model model, Principal principal){
        PostResponseDTO dto = postsService.selectBoardContent(id);
        model.addAttribute("posts", dto);

        if(principal != null){
            String email = principal.getName();
            model.addAttribute("isMyPosts", email.equals(dto.getWriter()));
        }
    }
    @GetMapping("/posts/modify")
    public void modify(@RequestParam("postsNum") Long id, Model model){
        model.addAttribute("posts", postsService.selectBoardContent(id));
    }
    /**
     * 게시글 작성
     * @param dto
     */
    @PostMapping("/posts/insert")
    public String insert(@ModelAttribute PostRequestDTO dto, Principal principal){
        String email = principal.getName();
        postsService.insertPost(dto, email);

        return "redirect:/";
    }

    /**
     * 게시글 수정
     * @param dto
     * @param principal
     */
    @PostMapping("/posts/update")
    public String update(@ModelAttribute PostRequestDTO dto, Principal principal){
        String email = principal.getName();
        postsService.updatePost(dto, email);

        return "redirect:/";
    }

    /**
     * 게시글 삭제
     * @param id
     */
    @PostMapping("/posts/delete")
    public void delete(@RequestParam("id") Long id, Principal principal){
        String email = principal.getName();
        postsService.deletePost(id, email);
    }
}