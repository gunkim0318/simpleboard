package application.jpa.repository;

import application.jpa.domain.Posts;
import application.jpa.domain.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Reply 저장소
 */
public interface ReplyRepository extends JpaRepository<Reply, Long> {
    List<Reply> findAllByPosts(Posts posts);

    Integer countByPosts(Posts posts);
}