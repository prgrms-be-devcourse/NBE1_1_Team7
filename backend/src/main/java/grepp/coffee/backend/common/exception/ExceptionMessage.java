package grepp.coffee.backend.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionMessage {

    // Product
    PRODUCT_NOT_FOUND("해당 상품을 찾을 수 없습니다."),


    // Order
    ORDER_NOT_FOUND("해당 주문을 찾을 수 없습니다."),
    ORDER_STATUS_NOT_FENDING("해당 주문은 배송중이거나 완료 상태입니다."),

    // Member
    MEMBER_NOT_FOUND("해당 사용자를 찾을 수 없습니다."),
    MEMBER_IS_PRESENT("해당 이메일로 가입된 계정이 이미 있습니다."),
    MEMBER_NOT_LOGIN("로그인을 먼저 진행해주세요."),
    MEMBER_LOGIN_FAIL("이메일과 비밀번호를 다시 확인해주세요.")

    ;
    private final String text;
}
