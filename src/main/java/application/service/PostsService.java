package application.service;

import application.domain.Member;
import application.domain.MemberRepository;
import application.domain.Posts;
import application.domain.PostRepository;
import application.dto.request.PostsRequestDTO;
import application.dto.response.PostsResponseDTO;
import application.dto.PageDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public int insertPost(PostsRequestDTO dto, String email){
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
    public List<PostsResponseDTO> selectPostList(PageDTO pagingDTO){
        return postRepository.findAllByOrderByIdDesc(pagingDTO.toEntity()).stream().map(PostsResponseDTO::new).collect(Collectors.toList());
    }
    @Transactional
    public void updatePost(PostsRequestDTO dto, String email){
        Posts posts = postRepository.findById(dto.getId()).get();

        boolean isMyPost = posts.getMember().getEmail().equals(email);

        if(isMyPost){
            posts.update(dto.getTitle(), dto.getContent());
            postRepository.save(posts);
        }
    }
    @Transactional
    public void deletePost(Long id, String email){
        Posts posts = postRepository.findById(id).get();

        boolean isMyPost = posts.getMember().getEmail().equals(email);

        if(isMyPost){
            postRepository.delete(posts);
        }
    }
    @Transactional
    public PostsResponseDTO selectBoardContent(Long id){
        Posts board = postRepository.findById(id).get();
        board.hitUp();

        postRepository.save(board);

        return new PostsResponseDTO(board);
    }
    public Integer selectTotalPostCnt(){
        return (int)postRepository.count();
    }
}