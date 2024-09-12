package grepp.coffee.backend.model.repository.member;

import grepp.coffee.backend.model.entity.cart.Cart;
import grepp.coffee.backend.model.entity.member.Member;
import grepp.coffee.backend.model.entity.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    // 사용자의 장바구니 목록을 조회하는 메서드
    List<Cart> findByMember(Member member);

    // 멤버 및 상품 가져오는 메서드
    Cart findByMemberAndProduct(Member member, Product product);
}
