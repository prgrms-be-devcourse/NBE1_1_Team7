package grepp.coffee.backend.model.service.order;

import grepp.coffee.backend.common.exception.ExceptionMessage;
import grepp.coffee.backend.common.exception.order.OrderException;
import grepp.coffee.backend.controller.order.request.OrderRegisterRequest;
import grepp.coffee.backend.controller.order.request.OrderUpdateRequest;
import grepp.coffee.backend.controller.orderitem.request.OrderItemRequest;
import grepp.coffee.backend.model.entity.member.Member;
import grepp.coffee.backend.model.entity.order.Order;
import grepp.coffee.backend.model.entity.orderitem.OrderItem;
import grepp.coffee.backend.model.entity.product.Product;
import grepp.coffee.backend.model.repository.order.OrderRepository;
import grepp.coffee.backend.model.repository.orderitem.OrderItemRepository;
import grepp.coffee.backend.model.service.member.CartService;
import grepp.coffee.backend.model.service.member.MemberService;
import grepp.coffee.backend.model.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static grepp.coffee.backend.model.entity.order.constant.OrderStatus.PENDING;
import static grepp.coffee.backend.model.entity.order.constant.OrderStatus.SHIPPING;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductService productService;
    private final MemberService memberService;
    private final CartService cartService;

    // 사용자가 주문한 목록 조회 (memberId 사용)
    public List<Order> getUserOrders(Long memberId) {

        // 멤버 조회
        Member member = memberService.findByIdOrThrowMemberException(memberId);

        return orderRepository.findByMember(member);
    }

    // 주문 등록
    @Transactional
    public void registerOrder(OrderRegisterRequest request) {
        // 회원 조회
        Member member = memberService.findByIdOrThrowMemberException(request.getMemberId());

        // 주문 저장
        Order order = Order.builder()
                .member(member)
                .orderStatus(PENDING)
                .build();
        orderRepository.save(order);

        for (OrderItemRequest itemRequest : request.getOrderItems()) {
            // 상품 조회
            Product product = productService.findByIdOrThrowProductException(itemRequest.getProductId());

            //주문수량 증가
            product.increaseOrderCount(itemRequest.getQuantity());

            // OrderItem 생성
            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .product(product)
                    .quantity(itemRequest.getQuantity())
                    .build();
            orderItemRepository.save(orderItem);

            // 장바구니에서 해당 상품 삭제 (주문 완료 시)
            cartService.deleteCartByOrder(product.getProductId(), member.getMemberId());
        }
    }

    // 주문 메뉴 수정하기
    @Transactional
    public void updateOrderMenu(Long orderId, OrderUpdateRequest request) {

        // 주문 조회
        Order order = findByIdOrThrowOrderException(orderId);

        // 주문 상태가 배송중 or 배송완료 예외처리
        if (!order.getOrderStatus().equals(PENDING)) {
            throw new OrderException(ExceptionMessage.ORDER_STATUS_NOT_FENDING);
        }

        // 기존 주문 항목 삭제
        List<OrderItem> existingOrderItems = orderItemRepository.findByOrder(order);
        orderItemRepository.deleteAll(existingOrderItems);

        // 기존 주문 수량 감소
        productService.decreaseProductOrderCount(existingOrderItems);

        // 새로운 주문 항목 저장
        for (OrderItemRequest itemRequest : request.getUpdatedOrderItems()) {
            // 상품 조회
            Product product = productService.findByIdOrThrowProductException(itemRequest.getProductId());

            //주문수량 증가
            product.increaseOrderCount(itemRequest.getQuantity());

            // 새로운 OrderItem 생성 및 저장
            OrderItem updatedOrderItem = OrderItem.builder()
                    .order(order)
                    .product(product)
                    .quantity(itemRequest.getQuantity())
                    .build();
            orderItemRepository.save(updatedOrderItem);
        }
    }

    // 주문 취소
    @Transactional
    public void deleteOrder(Long orderId) {

        // 주문 조회
        Order order = findByIdOrThrowOrderException(orderId);

        // 상태 예외처리
        if (!order.getOrderStatus().equals(PENDING)) {
            throw new OrderException(ExceptionMessage.ORDER_STATUS_NOT_FENDING);
        }

        // 주문 수량 감소
        List<OrderItem> oderItems = orderItemRepository.findByOrder(order);
        productService.decreaseProductOrderCount(oderItems);

        orderRepository.delete(order);
    }

    // 관리자가 모든 주문 목록 조회
    public List<Order> getAdminOrders() {
        return orderRepository.findAll();
    }

    // 배송 시작 일괄 처리
    @Transactional
    public void startShipping() {

        LocalDateTime yesterdayAfternoon = LocalDateTime.now().minusDays(1).with(LocalTime.of(14, 0));
        LocalDateTime todayBefore2pm = LocalDateTime.now().with(LocalTime.of(13, 59));

        List<Order> orders = orderRepository.findOrdersByCreatedAtBetween(yesterdayAfternoon, todayBefore2pm);

        for (Order order : orders) {
            if (order.getOrderStatus().equals(PENDING)) {
                order.updateOrderStatus(SHIPPING);
            }
        }
    }

    // 주문 조회 예외처리
    public Order findByIdOrThrowOrderException(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> {
                    log.warn(">>>> {} : {} <<<<", orderId, ExceptionMessage.ORDER_NOT_FOUND);
                    return new OrderException(ExceptionMessage.ORDER_NOT_FOUND);
                });
    }
}
