package application.service;

import application.jpa.domain.Member;
import application.jpa.domain.Posts;
import application.jpa.domain.Reply;
import application.jpa.repository.MemberRepository;
import application.jpa.repository.PostsRepository;
import application.jpa.repository.ReplyRepository;
import application.service.dto.ReplyResponseDTO;
import application.web.dto.ReplyRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReplyService {
    private final ReplyRepository replyRepository;
    private final MemberRepository memberRepository;
    private final PostsRepository postsRepository;

    @Transactional
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
    @Transactional
    public void writeReply(ReplyRequestDTO requestDTO, String email){
        Member member = memberRepository.findByEmail(email);
        Posts posts = postsRepository.findById(requestDTO.getPostsId()).get();

        replyRepository.save(requestDTO.toEntity(member, posts));
    }
    public void modifyReply(ReplyRequestDTO requestDTO, String email){
        Reply reply = replyRepository.findById(requestDTO.getReplyId()).get();

        if(email.equals(reply.getMember().getEmail())){
            reply.update(requestDTO.getContent());
            replyRepository.save(reply);
        }
    }
    public void deleteReply(long replyId, String email){
        Reply reply = replyRepository.findById(replyId).get();

        if(email.equals(reply.getMember().getEmail())){
            replyRepository.delete(reply);
        }
    }
    public Integer getReplyCnt(long postsId){
        Posts posts = postsRepository.findById(postsId).get();
        return replyRepository.countByPosts(posts);
    }
}