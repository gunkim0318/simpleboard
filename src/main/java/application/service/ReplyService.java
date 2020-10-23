package application.service;

import application.jpa.domain.Member;
import application.jpa.domain.Posts;
import application.jpa.domain.Reply;
import application.jpa.repository.MemberRepository;
import application.jpa.repository.PostsRepository;
import application.jpa.repository.ReplyRepository;
import application.service.dto.ReplyResponseDTO;
import application.web.dto.PageRequestDTO;
import application.web.dto.ReplyRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReplyService {
    private final ReplyRepository replyRepository;
    private final MemberRepository memberRepository;
    private final PostsRepository postsRepository;

    @Transactional()
    public List<ReplyResponseDTO> getReplyList(Long postsId, PageRequestDTO pageDTO){
        Posts posts = postsRepository.findById(postsId)
                .orElseThrow(() -> new IllegalArgumentException(String.format("해당 %d로 등록된 게시이 없습니다.", postsId)));
        List<Reply> list = replyRepository.findAllByPostsOrderByIdDesc(posts, pageDTO.toEntity());
        List<ReplyResponseDTO> dtoList = new ArrayList<>();
        for(Reply reply : list){
            dtoList.add(ReplyResponseDTO.builder()
                    .rno(reply.getId())
                    .content(reply.getContent())
                    .creDatetime(reply.getCreateDate())
                    .writer(reply.getMember().getNickname())
                    .build());
        }
        return dtoList;
    }
    @Transactional(readOnly = true)
    public void writeReply(ReplyRequestDTO requestDTO, String email){
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException(String.format("해당 %s로 등록된 유저가 없습니다.", email)));
        Posts posts = postsRepository.findById(requestDTO.getPostsId())
                .orElseThrow(() -> new IllegalArgumentException(String.format("해당 %d로 등록된 게시이 없습니다.", requestDTO.getPostsId())));

        replyRepository.save(requestDTO.toEntity(member, posts));
    }
    public Integer modifyReply(ReplyRequestDTO requestDTO, String email){
        Reply reply = replyRepository.findById(requestDTO.getReplyId())
                .orElseThrow(() -> new IllegalArgumentException(String.format("해당 %d로 등록된 댓글이 없습니다.", requestDTO.getReplyId())));

        if(email.equals(reply.getMember().getEmail())){
            reply.update(requestDTO.getContent());
            replyRepository.save(reply);
            return 1;
        }
        return 0;
    }
    public Integer deleteReply(long replyId, String email){
        Reply reply = replyRepository.findById(replyId)
                .orElseThrow(() -> new IllegalArgumentException(String.format("해당 %d로 등록된 댓글이 없습니다.", replyId)));

        if(email.equals(reply.getMember().getEmail())){
            replyRepository.delete(reply);

            return 1;
        }
        return 0;
    }
    @Transactional(readOnly = true)
    public Integer getReplyCnt(long postsId){
        Posts posts = postsRepository.findById(postsId)
                .orElseThrow(() -> new IllegalArgumentException(String.format("해당 %d로 등록된 게시글이 없습니다.", postsId)));
        return replyRepository.countByPosts(posts);
    }
}