package application.service;

import application.domain.Board;
import application.domain.BoardRepository;
import application.dto.BoardRequestDTO;
import application.dto.BoardResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardRepository boardRepository;

    public void insertBoard(BoardRequestDTO dto){
        boardRepository.save(dto.toEntity());
    }
    public List<BoardResponseDTO> selectBoardList(){
        return boardRepository.findAll().stream().map(BoardResponseDTO::new).collect(Collectors.toList());
    }
    public void updateBoard(Long id, BoardRequestDTO dto){
        Board board = boardRepository.findById(id).get();

        board.update(dto.getTitle(), dto.getContent());

        boardRepository.save(board);
    }
    public void deleteBoard(Long id){
        boardRepository.deleteById(id);
    }
    public BoardResponseDTO selectBoardContent(Long id){
        Board board = boardRepository.findById(id).get();
        board.hitUp();

        boardRepository.save(board);

        return new BoardResponseDTO(board);
    }
}