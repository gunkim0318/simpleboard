package application.web;

import application.dto.PostRequestDTO;
import application.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/post")
public class PostApiController {
    private final PostService boardService;

    @PostMapping("/insert")
    public void insert(@RequestBody PostRequestDTO dto){
        boardService.insertPost(dto);
    }
    @PutMapping("/update/{id}")
    public void update(@PathVariable Long id, @RequestBody PostRequestDTO dto){
        boardService.updatePost(id, dto);
    }
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id){
        boardService.deletePost(id);
    }
}