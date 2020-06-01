package application.service;

import application.jpa.domain.Member;
import application.jpa.domain.Posts;
import application.jpa.repository.MemberRepository;
import application.jpa.repository.PostsRepository;
import application.jpa.repository.ReplyRepository;
import application.service.dto.ReplyResponseDTO;
import application.web.dto.ReplyRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReplyService {
    private final ReplyRepository replyRepository;
    private final MemberRepository memberRepository;
    private final PostsRepository postsRepository;

    public List<ReplyResponseDTO> getReplyList(Long postsId){
        Posts posts = postsRepository.findById(postsId).get();

        List<ReplyResponseDTO> returnList = new ArrayList<ReplyResponseDTO>();
        replyRepository.findAllByPosts(posts).stream().forEach(reply -> {
            ReplyResponseDTO responseDTO = ReplyResponseDTO.builder()
            .content(reply.getContent())
            .creDatetime(reply.getCreateDate())
            .writer(reply.getMember().getNickname())
            .build();
            returnList.add(responseDTO);
        });
        return returnList;
    }
    public void writeReply(ReplyRequestDTO requestDTO, String email){
        Member member = memberRepository.findByEmail(email);
        Posts posts = postsRepository.findById(requestDTO.getPostsId()).get();

        replyRepository.save(requestDTO.toEntity(member, posts));
    }
    public void 
}