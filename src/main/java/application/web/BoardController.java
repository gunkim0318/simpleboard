package application.web;

import application.dto.BoardRequestDTO;
import application.dto.BoardResponseDTO;
import application.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/board")
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/list")
    public List<BoardResponseDTO> list(){
        return boardService.selectBoardList();
    }
    @PostMapping("/insert")
    public void insert(@RequestBody BoardRequestDTO dto){
        boardService.insertBoard(dto);
    }
    @PutMapping("/update/{id}")
    public void update(@PathVariable Long id, @RequestBody BoardRequestDTO dto){
        boardService.updateBoard(id, dto);
    }
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id){
        boardService.deleteBoard(id);
    }
}