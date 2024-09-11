package grepp.coffee.backend.model.service.member;

import grepp.coffee.backend.common.exception.ExceptionMessage;
import grepp.coffee.backend.common.exception.member.CartException;
import grepp.coffee.backend.common.exception.member.MemberException;
import grepp.coffee.backend.common.exception.product.ProductException;
import grepp.coffee.backend.model.entity.cart.Cart;
import grepp.coffee.backend.model.entity.member.Member;
import grepp.coffee.backend.model.entity.product.Product;
import grepp.coffee.backend.model.repository.member.CartRepository;
import grepp.coffee.backend.model.repository.member.MemberRepository;
import grepp.coffee.backend.model.repository.product.ProductRepository;
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
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    // 장바구니 추가
    @Transactional
    public void addCart(Long memberId, Long productId, int quantity) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> {
                    log.warn(">>>> {} : {} <<<<", memberId, ExceptionMessage.MEMBER_NOT_FOUND);
                    return new MemberException(ExceptionMessage.MEMBER_NOT_FOUND);
                });

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> {
                    log.warn(">>>> {} : {} <<<<", productId, ExceptionMessage.PRODUCT_NOT_FOUND);
                    return new ProductException(ExceptionMessage.PRODUCT_NOT_FOUND);
                });

        Cart cart = Cart.builder()
                .member(member)
                .product(product)
                .quantity(quantity)
                .build();

        cartRepository.save(cart);
    }

    // 장바구니 목록 조회
    @Transactional(readOnly = true)
    public List<Cart> getCart(Long memberId) {
        return cartRepository.findByMemberId(memberId);
    }

    // 장바구니 수정
    @Transactional
    public void updateCart(Long cartId, int quantity) {
        Cart existingCart = cartRepository.findById(cartId)
                .orElseThrow(() -> {
                    log.warn(">>>> {} : {} <<<<", cartId, ExceptionMessage.CART_NOT_FOUND);
                    return new CartException(ExceptionMessage.CART_NOT_FOUND);
                });


        Cart updatedCart = Cart.builder()
                .cartId(existingCart.getCartId())
                .member(existingCart.getMember())
                .product(existingCart.getProduct())
                .quantity(quantity)
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
    public void deleteCartByOrder(Long memberId, Long productId) {
        cartRepository.deleteByMemberIdAndProductId(memberId, productId);
    }
}
