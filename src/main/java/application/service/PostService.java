package application.service;

import application.domain.Post;
import application.domain.PostRepository;
import application.dto.PostRequestDTO;
import application.dto.PostResponseDTO;
import application.dto.PageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;

    public void insertPost(PostRequestDTO dto){
        postRepository.save(dto.toEntity());
    }
    public List<PostResponseDTO> selectPostList(PageDTO pagingDTO){
        return postRepository.findAllByOrderByIdDesc(pagingDTO.toEntity()).stream().map(PostResponseDTO::new).collect(Collectors.toList());
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
    public Integer selectTotalPostCnt(){
        return (int)postRepository.count();
    }
}