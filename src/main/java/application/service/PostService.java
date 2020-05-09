package application.service;

import application.domain.Member;
import application.domain.MemberRepository;
import application.domain.Post;
import application.domain.PostRepository;
import application.dto.request.PostRequestDTO;
import application.dto.response.PostResponseDTO;
import application.dto.PageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public void insertPost(PostRequestDTO dto, String email){
        Member member = memberRepository.findByEmail(email);
        dto.setMember(member);
        postRepository.save(dto.toEntity());
    }
    public List<PostResponseDTO> selectPostList(PageDTO pagingDTO){
        return postRepository.findAllByOrderByIdDesc(pagingDTO.toEntity()).stream().map(PostResponseDTO::new).collect(Collectors.toList());
    }
    public void updatePost(Long id, PostRequestDTO dto, String email){
        Post post = postRepository.findById(id).get();

        boolean isMyPost = post.getMember().getEmail().equals(email);

        if(isMyPost){
            post.update(dto.getTitle(), dto.getContent());
            postRepository.save(post);
        }
    }
    public void deletePost(Long id, String email){
        Post post = postRepository.findById(id).get();

        boolean isMyPost = post.getMember().getEmail().equals(email);

        if(isMyPost){
            postRepository.delete(post);
        }
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