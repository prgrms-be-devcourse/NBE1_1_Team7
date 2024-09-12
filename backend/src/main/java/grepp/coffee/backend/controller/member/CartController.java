package grepp.coffee.backend.controller.member;

import grepp.coffee.backend.controller.member.request.CartRegisterRequest;
import grepp.coffee.backend.controller.member.request.CartUpdateRequest;
import grepp.coffee.backend.model.entity.cart.Cart;
import grepp.coffee.backend.model.service.member.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/cart")
@RestController
public class CartController {

    private final CartService cartService;

    // 장바구니에 항목 추가
    @PostMapping("/")
    public ResponseEntity<Void> addCart(@Valid @RequestBody CartRegisterRequest request) {
        cartService.addCart(request);
        return ResponseEntity.ok().build();
    }

    // 장바구니 목록 조회
    @GetMapping("/{memberId}")
    public ResponseEntity<List<Cart>> getCart(@PathVariable Long memberId) {
        List<Cart> cartItems = cartService.getCart(memberId);
        return ResponseEntity.ok(cartItems);
    }

    // 장바구니 수정
    @PutMapping("/{cartId}")
    public ResponseEntity<Void> updateCart(@PathVariable Long cartId, @Valid @RequestBody CartUpdateRequest request) {
        cartService.updateCart(cartId, request);
        return ResponseEntity.ok().build();
    }

    // 장바구니 전체 항목 삭제
    @DeleteMapping("/{cartId}")
    public ResponseEntity<Void> deleteCart(@PathVariable Long cartId) {
        cartService.deleteCart(cartId);
        return ResponseEntity.ok().build();
    }

}
