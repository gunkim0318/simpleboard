package application.web;

import application.service.ReplyService;
import application.service.dto.ReplyResponseDTO;
import application.util.ErrorsTransUtil;
import application.util.PagingUtil;
import application.web.dto.PageRequestDTO;
import application.web.dto.ReplyRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 댓글 컨트롤러
 */
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/reply/api")
@RestController
public class ReplyApiController {
    private final ReplyService replyService;

    @GetMapping("/cnt/{postsId}")
    public ResponseEntity<Integer> findPostsIdByReplyCnt(@PathVariable long postsId){
        return new ResponseEntity<>(replyService.getReplyCnt(postsId), HttpStatus.OK);
    }

    /**
     * 댓글 목록 조회
     * @param postsId
     * @param pageNum
     * @return
     */
    @GetMapping("/{postsId}/{pageNum}")
    public ResponseEntity<Map<String, Object>> findPostsIdByReplyList(@PathVariable long postsId, @PathVariable int pageNum){
        PageRequestDTO pageDTO = new PageRequestDTO(pageNum, 5);
        int replyCnt = replyService.getReplyCnt(postsId);
        PagingUtil paging = new PagingUtil(pageDTO, replyCnt);

        List<ReplyResponseDTO> replyList =  replyService.getReplyList(postsId, pageDTO);
        Map<String, Object> map = new HashMap<>();
        map.put("list", replyList);
        map.put("paging", paging);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    /**
     * 댓글 입력
     * @param replyRequestDTO
     * @param principal
     * @return
     */
    @PostMapping
    public ResponseEntity<Object> writeReply(@Validated @RequestBody ReplyRequestDTO replyRequestDTO, BindingResult errors, Principal principal){
        if(errors.hasFieldErrors("content")){
            ErrorsTransUtil errorsUtil = new ErrorsTransUtil(errors);
            return new ResponseEntity<>(errorsUtil.getMap(), HttpStatus.BAD_REQUEST);
        }
        replyService.writeReply(replyRequestDTO, principal.getName());

        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    /**
     * 댓글 수정
     * @param rno
     * @param dto
     * @param principal
     * @return
     */
    @PutMapping("/{rno}")
    public ResponseEntity updateReply(@PathVariable long rno, @RequestBody ReplyRequestDTO dto, Principal principal) {
        String email = principal.getName();
        boolean isSuccess = replyService.modifyReply(dto, email) == 1;
        if(isSuccess){
            return new ResponseEntity("success", HttpStatus.OK);
        }
        return new ResponseEntity("fail", HttpStatus.BAD_REQUEST);
    }

    /**
     * 댓글 삭제
     * @param rno
     * @param principal
     * @return
     */
    @DeleteMapping("/{rno}")
    public ResponseEntity deleteReply(@PathVariable long rno, Principal principal){
        String email = principal.getName();
        boolean isSuccess = replyService.deleteReply(rno, email) == 1;

        if(isSuccess){
            return new ResponseEntity("success", HttpStatus.OK);
        }
        return new ResponseEntity("fail", HttpStatus.BAD_REQUEST);
    }
}