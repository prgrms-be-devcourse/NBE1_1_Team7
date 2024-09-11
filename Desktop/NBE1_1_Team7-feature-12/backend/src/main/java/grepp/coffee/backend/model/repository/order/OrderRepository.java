package grepp.coffee.backend.model.repository.order;


import grepp.coffee.backend.model.entity.member.Member;
import grepp.coffee.backend.model.entity.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    // 멤버 기준으로 주문 내역을 조회하는 메서드
    List<Order> findByMember(Member member);

    // 특정 시간 범위 내의 주문 목록 조회 (createdAt 필드 기준으로 조회)
    List<Order> findOrdersByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
}
