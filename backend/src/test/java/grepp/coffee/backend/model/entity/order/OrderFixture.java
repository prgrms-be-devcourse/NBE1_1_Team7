package grepp.coffee.backend.model.entity.order;

import grepp.coffee.backend.model.entity.member.Member;
import grepp.coffee.backend.model.entity.order.constant.OrderStatus;

public class OrderFixture {

    //테스트용 주문 등록
    public static Order registerOrder(Member member) {
        return Order.builder()
                .member(member)
                .orderStatus(OrderStatus.PENDING)
                .build();
    }
}
