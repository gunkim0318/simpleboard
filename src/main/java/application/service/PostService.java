package application.service;

import application.domain.Post;
import application.domain.PostRepository;
import application.dto.PostRequestDTO;
import application.dto.PostResponseDTO;
import application.util.PagingUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;

    public void insertPost(PostRequestDTO dto){
        postRepository.save(dto.toEntity());
    }
    public List<PostResponseDTO> selectBoardList(PagingUtil pagingUtil){
        pagingUtil.setTotalPostNum((int) postRepository.count());
        return postRepository.findAll(pagingUtil.toEntity()).stream().map(PostResponseDTO::new).collect(Collectors.toList());
    }
    public void updatePost(Long id, PostRequestDTO dto){
        Post board = postRepository.findById(id).get();

        board.update(dto.getTitle(), dto.getContent());

        postRepository.save(board);
    }
    public void deletePost(Long id){
        postRepository.deleteById(id);
    }
    public PostResponseDTO selectBoardContent(Long id){
        Post board = postRepository.findById(id).get();
        board.hitUp();

        postRepository.save(board);

        return new PostResponseDTO(board);
    }
}