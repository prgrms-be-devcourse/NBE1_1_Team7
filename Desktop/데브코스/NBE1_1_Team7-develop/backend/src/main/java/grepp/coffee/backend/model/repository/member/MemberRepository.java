package grepp.coffee.backend.model.repository.member;

import grepp.coffee.backend.model.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
