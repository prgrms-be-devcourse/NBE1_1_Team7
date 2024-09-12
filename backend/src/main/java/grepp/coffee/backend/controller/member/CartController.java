package grepp.coffee.backend.controller.member;

import grepp.coffee.backend.controller.member.request.CartRegisterRequest;
import grepp.coffee.backend.controller.member.request.CartUpdateRequest;
import grepp.coffee.backend.model.entity.cart.Cart;
import grepp.coffee.backend.model.service.member.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "장바구니 API")
public class CartController {

    private final CartService cartService;

    @PostMapping("/")
    @Operation(summary = "장바구니 상품 등록", description = "장바구니에 상품을 등록하는 API")
    public ResponseEntity<Void> addCart(@Valid @RequestBody CartRegisterRequest request) {
        cartService.addCart(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{memberId}")
    @Operation(summary = "장바구니 목록 조회", description = "장바구니 목록을 조회하는 API")

    public ResponseEntity<List<Cart>> getCart(@PathVariable Long memberId) {
        List<Cart> cartItems = cartService.getCart(memberId);
        return ResponseEntity.ok(cartItems);
    }


    @PutMapping("/{cartId}")
    @Operation(summary = "장바구니 상품 수정", description = "회원가입 API")
    public ResponseEntity<Void> updateCart(@PathVariable Long cartId, @Valid @RequestBody CartUpdateRequest request) {
        cartService.updateCart(cartId, request);
        return ResponseEntity.ok().build();
    }


    @DeleteMapping("/{cartId}")
    @Operation(summary = "장바구니 전체 삭제", description = "장바구니에 있는 모든 상품을 삭제하는 API")
    public ResponseEntity<Void> deleteCart(@PathVariable Long cartId) {
        cartService.deleteCart(cartId);
        return ResponseEntity.ok().build();
    }
}
