package grepp.coffee.backend.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionMessage {

    // Product
    PRODUCT_NOT_FOUND("해당 상품을 찾을 수 없습니다."),
    PRODUCT_DISCOUNT_BAE_REQUEST("상품 가격보다 큰 할인 가격은 적용할 수 없습니다."),

    // Order
    ORDER_NOT_FOUND("해당 주문을 찾을 수 없습니다."),
    ORDER_STATUS_NOT_FENDING("해당 주문은 배송중이거나 완료 상태입니다."),

    // Review
    REVIEW_NOT_FOUND("해당 리뷰를 찾을 수 없습니다."),
  
    // Member
    MEMBER_NOT_FOUND("해당 사용자를 찾을 수 없습니다.")

    // Question
    QUESTION_NOT_FOUND("해당 FAQ를 찾을 수 없습니다."),

    ;
    private final String text;
}
