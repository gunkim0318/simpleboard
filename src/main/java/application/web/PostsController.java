package application.web;

import application.service.PostsService;
import application.service.ReplyService;
import application.service.dto.PostsResponseDTO;
import application.util.ErrorsTransUtil;
import application.web.dto.PostsRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

        log.info("pal : "+principal);
        if(principal != null){
            String email = principal.getName();
            log.info("email : "+email);
            log.info("writer : "+dto.getEmail());
            model.addAttribute("isMyPosts", email.equals(dto.getEmail()));
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
    public String insert(@Validated @ModelAttribute PostsRequestDTO dto, BindingResult errors, Model model, Principal principal, RedirectAttributes rttr){
        if(errors.hasErrors()){
            ErrorsTransUtil errorsUtil = new ErrorsTransUtil(errors);
            model.addAttribute("errors", errorsUtil.getMap());
            model.addAttribute("member", dto);
            return "/posts/add";
        }
        String email = principal.getName();
        postsService.insertPosts(dto, email);

        rttr.addFlashAttribute("msg", "게시글 입력에 성공했습니다.");
        return "redirect:/";
    }

    /**
     * 게시글 수정
     * @param dto
     * @param principal
     */
    @PostMapping("/update")
    public String update(@Validated @ModelAttribute PostsRequestDTO dto, BindingResult errors, Model model, Principal principal, RedirectAttributes rttr){
        if(errors.hasErrors()){
            ErrorsTransUtil errorsUtil = new ErrorsTransUtil(errors);

            model.addAttribute("errors", errorsUtil.getMap());
            model.addAttribute("posts", dto);

            return "/posts/modify";
        }
        String email = principal.getName();
        postsService.updatePosts(dto, email);

        rttr.addFlashAttribute("msg", "게시글 수정에 성공했습니다.");
        return "redirect:/";
    }

    /**
     * 게시글 삭제
     * @param id
     */
    @PostMapping("/delete")
    public String delete(@RequestParam("postsNum") Long id, Principal principal, RedirectAttributes rttr){
        String email = principal.getName();
        postsService.deletePosts(id, email);

        rttr.addFlashAttribute("msg", "게시글 삭제에 성공했습니다.");
        return "redirect:/";
    }
}