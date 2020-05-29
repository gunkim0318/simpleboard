package application.jpa.repository;

import application.jpa.domain.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Reply 저장소
 */
public interface ReplyRepository extends JpaRepository<Reply, Long> {
}