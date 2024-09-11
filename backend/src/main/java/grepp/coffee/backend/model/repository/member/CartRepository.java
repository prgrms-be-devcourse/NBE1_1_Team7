package grepp.coffee.backend.model.repository.member;

import grepp.coffee.backend.model.entity.cart.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    // 사용자의 장바구니 목록을 조회하는 메서드
    List<Cart> findByMemberId(Long memberId);

    // 사용자 및 상품에 대안 장바구니 삭제 메서드
    void deleteByMemberIdAndProductId(Long memberId, Long ProductId);
}
