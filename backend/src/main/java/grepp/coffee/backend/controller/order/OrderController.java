package grepp.coffee.backend.controller.order;


import grepp.coffee.backend.controller.order.request.OrderRegisterRequest;
import grepp.coffee.backend.controller.order.request.OrderUpdateRequest;
import grepp.coffee.backend.model.entity.order.Order;
import grepp.coffee.backend.model.service.order.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j  // 로그 사용
@RequiredArgsConstructor
@RequestMapping("/order")
@RestController
@Tag(name = "주문 API")
public class OrderController {


    private final OrderService orderService;


    @GetMapping("/member/{memberId}")
    @Operation(summary = "사용가자 주문한 주문 목록 조회", description = "사용자의 주문 목록을 조회하는 API")

    public ResponseEntity<List<Order>> getUserOrders(@PathVariable Long memberId) {

        return ResponseEntity.ok().body(orderService.getUserOrders(memberId));
    }


    @PostMapping("/")
    @Operation(summary = "주문 등록", description = "주문을 등록하는 API")
    public ResponseEntity<Void> registerOrder(@Valid @RequestBody OrderRegisterRequest request) {
        orderService.registerOrder(request);
        return ResponseEntity.ok().build();
    }

    // 장바구니에서 주문 등록
    @PostMapping("/cart/{memberId}")
    @Operation(summary = "장바구니의 상품들을 주문 등록", description = "장바구니에 있는 상품들을 주문하는 API")
    ResponseEntity<Void> orderAllCartItems(@PathVariable Long memberId) {
        orderService.registerOrderCartItems(memberId);
        return ResponseEntity.ok().build();
    }


    @PutMapping("/{orderId}")
    @Operation(summary = "주문 상품 수정", description = "주문한 상품과 수량을 수정하는 API")

    public ResponseEntity<Void> updateOrderMenu(@PathVariable Long orderId,
                                                @Valid @RequestBody OrderUpdateRequest request) {
        orderService.updateOrderMenu(orderId, request);
        return ResponseEntity.ok().build();
    }


    @DeleteMapping("/{orderId}")
    @Operation(summary = "주문 정보 수정", description = "주문 정보 중 우편번호와 주소를 수정하는 API")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/admin")
    @Operation(summary = "[관리자] 모든 주문 목록 조회", description = "관리자가 모든 주문 목록을 조회하는 API")
    public ResponseEntity<List<Order>> getAdminOrders() {
        return ResponseEntity.ok().body(orderService.getAdminOrders());
    }

    // 관리자의 배송 시작 일괄 처리
    @PostMapping("/admin")
    @Operation(summary = "[관리자] 배송 시작 일괄 처리", description = "관리자가 전날 오후 2시부터 금일 오후2시까지의 주문을 " +
            "배송 처리하는 API")
    public ResponseEntity<Void> startShipping() {
        orderService.startShipping();
        return ResponseEntity.ok().build();
    }

}