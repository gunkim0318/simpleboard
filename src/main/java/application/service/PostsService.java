package application.service;

import application.jpa.domain.Member;
import application.jpa.repository.MemberRepository;
import application.jpa.domain.Posts;
import application.jpa.repository.PostsRepository;
import application.web.dto.PostsRequestDTO;
import application.service.dto.PostsResponseDTO;
import application.web.dto.PageRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 게시글 Service
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;
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

        postsRepository.save(dto.toEntity());
        return 1;
    }
    public List<PostsResponseDTO> selectPostList(PageRequestDTO pagingDTO){
        return postsRepository.findAllByOrderByIdDesc(pagingDTO.toEntity()).stream().map(PostsResponseDTO::new).collect(Collectors.toList());
    }
    @Transactional
    public void updatePost(PostsRequestDTO dto, String email){
        Posts posts = postsRepository.findById(dto.getId()).get();

        boolean isMyPost = posts.getMember().getEmail().equals(email);

        if(isMyPost){
            posts.update(dto.getTitle(), dto.getContent());
            postsRepository.save(posts);
        }
    }
    @Transactional
    public void deletePost(Long id, String email){
        Posts posts = postsRepository.findById(id).get();

        boolean isMyPost = posts.getMember().getEmail().equals(email);

        if(isMyPost){
            postsRepository.delete(posts);
        }
    }
    @Transactional
    public PostsResponseDTO selectBoardContent(Long id){
        Posts board = postsRepository.findById(id).get();
        board.hitUp();

        postsRepository.save(board);

        return new PostsResponseDTO(board);
    }
    public Integer selectTotalPostCnt(){
        return (int) postsRepository.count();
    }
}