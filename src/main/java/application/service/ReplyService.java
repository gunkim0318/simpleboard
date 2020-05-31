package application.service;

import application.jpa.repository.ReplyRepository;
import application.service.dto.ReplyResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReplyService {
    private final ReplyRepository replyRepository;

    public List<ReplyResponseDTO> selectReplyList(){

        return null;
    }
}