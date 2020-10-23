package application.service;

import application.jpa.domain.Member;
import application.jpa.domain.Posts;
import application.jpa.repository.MemberRepository;
import application.jpa.repository.PostsRepository;
import application.service.dto.PostsResponseDTO;
import application.web.dto.PageRequestDTO;
import application.web.dto.PostsRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public void insertPosts(PostsRequestDTO dto, String email){
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException(String.format("해당 %s로 등록된 사용자가 없습니다.", email)));

        postsRepository.save(dto.toEntity(member));
    }
    @Transactional(readOnly=true)
    public List<PostsResponseDTO> selectPostsList(PageRequestDTO pagingDTO){
        return postsRepository.findAllByOrderByIdDesc(pagingDTO.toEntity()).stream().map(PostsResponseDTO::new).collect(Collectors.toList());
    }
    @Transactional
    public void updatePosts(PostsRequestDTO dto, String email){
        Posts posts = postsRepository.findById(dto.getId())
                .orElseThrow(() -> new IllegalArgumentException("해당 번호로 등록된 게시글을 찾을 수 없습니다. id=="+dto.getId()));

        boolean isMyPost = posts.getMember().getEmail().equals(email);

        if(isMyPost){
            posts.update(dto.getTitle(), dto.getContent());
            postsRepository.save(posts);
        }
    }
    @Transactional
    public void deletePosts(Long id, String email){
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 번호로 등록된 게시글을 찾을 수 없습니다. id=="+id));

        boolean isMyPost = posts.getMember().getEmail().equals(email);

        if(isMyPost){
            postsRepository.delete(posts);
        }
    }
    @Transactional(readOnly = true)
    public PostsResponseDTO selectBoardContent(Long id){
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 번호로 등록된 게시글을 찾을 수 없습니다. id=="+id));

        posts.hitUp();

        postsRepository.save(posts);

        return new PostsResponseDTO(posts);
    }
    @Transactional(readOnly = true)
    public Integer selectTotalPostCnt(){
        return (int) postsRepository.count();
    }
}