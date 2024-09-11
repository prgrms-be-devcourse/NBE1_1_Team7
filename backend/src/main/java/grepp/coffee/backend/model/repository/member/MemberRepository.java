package grepp.coffee.backend.model.repository.member;

import grepp.coffee.backend.model.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    // 입력한 이메일로 가입된 회원 조회
    Member findByEmail(@Param("email") String email);
}
