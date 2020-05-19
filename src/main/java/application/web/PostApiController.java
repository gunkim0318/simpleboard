package application.web;

import application.dto.request.PostRequestDTO;
import application.service.PostService;
import application.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.UUID;

/**
 * 게시글 api
 */
@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/post")
public class PostApiController {
    private final PostService boardService;
    private final FileUtil fileUtil = new FileUtil();

    /**
     * 게시글 작성
     * @param dto
     */
    @PostMapping(value = "/insert", consumes = {"multipart/form-data"})
    public void insert(@ModelAttribute PostRequestDTO dto, Principal principal) throws IOException {
        FileUtil fileUtil = new FileUtil();
        fileUtil.save(dto.getFile());

        String email = principal.getName();
        boardService.insertPost(dto, email);
    }

    /**
     * 게시글 수정
     * @param id
     * @param dto
     */
    @PutMapping("/update/{id}")
    public void update(@PathVariable Long id, @RequestBody PostRequestDTO dto, Principal principal){
        String email = principal.getName();
        boardService.updatePost(id, dto, email);
    }

    /**
     * 게시글 삭제
     * @param id
     */
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id, Principal principal){
        String email = principal.getName();
        boardService.deletePost(id, email);
    }
}