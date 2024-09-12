package grepp.coffee.backend.model.service.member;

import grepp.coffee.backend.common.exception.ExceptionMessage;
import grepp.coffee.backend.common.exception.member.CartException;
import grepp.coffee.backend.controller.member.request.CartRegisterRequest;
import grepp.coffee.backend.controller.member.request.CartUpdateRequest;
import grepp.coffee.backend.model.entity.cart.Cart;
import grepp.coffee.backend.model.entity.member.Member;
import grepp.coffee.backend.model.entity.product.Product;
import grepp.coffee.backend.model.repository.member.CartRepository;
import grepp.coffee.backend.model.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final MemberService memberService;
    private final ProductService productService;

    // 장바구니 추가
    @Transactional
    public void addCart(CartRegisterRequest request) {
        Member member = memberService.findByIdOrThrowMemberException(request.getMemberId());

        Product product = productService.findByIdOrThrowProductException(request.getProductId());

        Cart cart = Cart.builder()
                .member(member)
                .product(product)
                .quantity(request.getQuantity())
                .build();

        cartRepository.save(cart);
    }

    // 장바구니 목록 조회
    @Transactional(readOnly = true)
    public List<Cart> getCart(Long memberId) {
        Member member = memberService.findByIdOrThrowMemberException(memberId);

        return cartRepository.findByMember(member);
    }

    // 장바구니 수정
    @Transactional
    public void updateCart(Long cartId, CartUpdateRequest request) {
        Cart cart = findByIdOrThrowCartException(cartId);

        Product product = productService.findByIdOrThrowProductException(request.getProductId());

        Cart updatedCart = Cart.builder()
                .cartId(cart.getCartId())
                .member(cart.getMember())
                .product(product)
                .quantity(request.getQuantity())
                .build();

        cartRepository.save(updatedCart);
    }

    // 장바구니 직접 삭제
    @Transactional
    public void deleteCart(Long cartId) {
        cartRepository.deleteById(cartId);
    }

    // 주문 처리 시, 장바구니 자동 삭제
    @Transactional
    public void deleteCartByOrder(Long memberId) {
        Member member = memberService.findByIdOrThrowMemberException(memberId);
        List<Cart> cartItems = cartRepository.findByMember(member);

        if (cartItems.isEmpty()) {
            throw new CartException(ExceptionMessage.CART_NOT_FOUND);
        }

        // 모든 장바구니 항목 삭제
        cartRepository.deleteAll(cartItems);
    }

    // 장바구니 조회 예외처리
    public Cart findByIdOrThrowCartException(Long cartId) {
        return cartRepository.findById(cartId)
                .orElseThrow(() -> {
                    log.warn(">>>> {} : {} <<<<", cartId, ExceptionMessage.CART_NOT_FOUND);
                    return new CartException(ExceptionMessage.CART_NOT_FOUND);
                });
    }
}
