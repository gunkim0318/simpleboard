package application.service;

import application.domain.Member;
import application.domain.MemberRepository;
import application.domain.Post;
import application.domain.PostRepository;
import application.dto.request.PostRequestDTO;
import application.dto.response.PostResponseDTO;
import application.dto.PageDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public int insertPost(PostRequestDTO dto, String email){
        Member member = memberRepository.findByEmail(email);
        dto.setMember(member);

        boolean isEmptyTitle = "".equals(dto.getTitle());
        boolean isEmptyContent = "".equals(dto.getContent());
        if(isEmptyTitle || isEmptyContent){
            return 0;
        }

        postRepository.save(dto.toEntity());
        return 1;
    }
    public List<PostResponseDTO> selectPostList(PageDTO pagingDTO){
        return postRepository.findAllByOrderByIdDesc(pagingDTO.toEntity()).stream().map(PostResponseDTO::new).collect(Collectors.toList());
    }
    @Transactional
    public void updatePost(PostRequestDTO dto, String email){
        Post post = postRepository.findById(dto.getId()).get();

        boolean isMyPost = post.getMember().getEmail().equals(email);

        if(isMyPost){
            post.update(dto.getTitle(), dto.getContent());
            postRepository.save(post);
        }
    }
    @Transactional
    public void deletePost(Long id, String email){
        Post post = postRepository.findById(id).get();

        boolean isMyPost = post.getMember().getEmail().equals(email);

        if(isMyPost){
            postRepository.delete(post);
        }
    }
    @Transactional
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