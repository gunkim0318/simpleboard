package application.web;

import application.service.ReplyService;
import application.service.dto.ReplyResponseDTO;
import application.web.dto.ReplyRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
 * 댓글 컨트롤러
 */
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/reply/api")
@RestController
public class ReplyApiController {
    private final ReplyService replyService;

    /**
     * 댓글 목록 조회
     * @param postsId
     * @return
     */
    @GetMapping("/{postsId}")
    public ResponseEntity<List<ReplyResponseDTO>> findPostsIdByReplyList(@PathVariable long postsId){
        List<ReplyResponseDTO> replyList =  replyService.getReplyList(postsId);

        return new ResponseEntity<>(replyList, HttpStatus.OK);
    }

    /**
     * 댓글 입력
     * @param replyRequestDTO
     * @param principal
     * @return
     */
    @PostMapping("/")
    public ResponseEntity writeReply(@RequestBody ReplyRequestDTO replyRequestDTO, Principal principal){
        replyService.writeReply(replyRequestDTO, principal.getName());

        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 댓글 수정
     * @param reply
     * @return
     */
    @PutMapping("/")
    public ResponseEntity updateReply(@RequestBody ReplyRequestDTO reply){
        return null;
    }

    /**
     * 댓글 삭제
     * @return
     */
    @DeleteMapping("/")
    public ResponseEntity deleteReply(){
        return null;
    }
}