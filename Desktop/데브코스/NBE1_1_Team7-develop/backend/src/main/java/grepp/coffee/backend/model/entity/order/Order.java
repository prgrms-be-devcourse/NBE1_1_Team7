package grepp.coffee.backend.model.entity.order;

import grepp.coffee.backend.model.entity.BaseEntity;
import grepp.coffee.backend.model.entity.member.Member;
import grepp.coffee.backend.model.entity.order.constant.OrderStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 외부에서 객체 생성 못하도록 제한
@Entity(name = "ORDERS")
@Table(name = "ORDERS")
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_ID", nullable = false)
    private Long orderId;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID", nullable = false)
    private Member member;

    @Enumerated(EnumType.STRING)
    @Column(name = "ORDER_STATUS", length = 50, nullable = false, columnDefinition = "VARCHAR(50) DEFAULT 'ACCEPTED'")
    private OrderStatus orderStatus;

    @Builder
    public Order(Long orderId, Member member, OrderStatus orderStatus) {
        this.orderId = orderId;
        this.member = member;
        this.orderStatus = orderStatus;
    }

    // 주문상태 업데이트
    public void updateOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

}