package Application.service.dto;

import application.service.dto.ReplyResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ReplyResponseDTOTests {
    @Test
    public void testDTO(){
        ReplyResponseDTO dto  = ReplyResponseDTO.builder()
                .content("안녕하세요")
                .creDatetime(LocalDateTime.now())
                .writer("gunkim")
                .build();

        log.info(dto.toString());
    }
}