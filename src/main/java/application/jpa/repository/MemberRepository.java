package application.jpa.repository;

import application.jpa.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Member 저장소
 */
public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByEmail(String email);
}