package application.jpa.repository;

import application.jpa.domain.Posts;
import application.jpa.domain.Reply;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Reply 저장소
 */
public interface ReplyRepository extends JpaRepository<Reply, Long> {
    Integer countByPosts(Posts posts);

    List<Reply> findAllByPostsOrderByIdDesc(Posts posts, Pageable paging);
}