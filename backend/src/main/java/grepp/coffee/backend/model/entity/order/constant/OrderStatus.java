package grepp.coffee.backend.model.entity.order.constant;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatus {
    PENDING("주문중"),
    SHIPPING("배송중"),
    DONE("배송완료")
    ;

    private final String text;
}
