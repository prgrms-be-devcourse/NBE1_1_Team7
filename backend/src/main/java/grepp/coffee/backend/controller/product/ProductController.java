package grepp.coffee.backend.controller.product;


import grepp.coffee.backend.common.exception.ExceptionMessage;
import grepp.coffee.backend.controller.product.request.ProductDetailResponse;
import grepp.coffee.backend.controller.product.request.ProductRegisterRequest;
import grepp.coffee.backend.controller.product.request.ProductUpdateRequest;
import grepp.coffee.backend.model.entity.member.Member;
import grepp.coffee.backend.model.entity.product.Product;
import grepp.coffee.backend.model.entity.product.constant.Category;
import grepp.coffee.backend.model.service.product.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/product")
@RestController
@Tag(name = "제품 API")
public class ProductController {

    private final ProductService productService;


    @GetMapping("")
    @Operation(summary = "상품 목록 조회", description = "모든 상품의 목록을 조회하는 API")
    public ResponseEntity<List<Product>> readProductList() {
        return ResponseEntity.ok().body(productService.readProductList());
    }

    // 상품 상세 조회
    @GetMapping("/{id}")
    @Operation(summary = "상품 상세 조회", description = "상품의 상세를 조회하는 API")

    public ResponseEntity<?> getProductDetails(@PathVariable("id") Long productId, HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("loginMember") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ExceptionMessage.MEMBER_NOT_LOGIN.getText());
        }

        ProductDetailResponse productDetailDTO = productService.getProductDetails(productId);
        return ResponseEntity.ok(productDetailDTO);
    }

    @PostMapping("/")
    @Operation(summary = "[관리자] 상품 등록", description = "관리자가 상품을 등록하는 API")
    public ResponseEntity<Void> registerProduct(@SessionAttribute(value = "loginMember", required = false) Member member,
                                                @Valid @RequestBody ProductRegisterRequest request) {
        productService.registerProduct(member, request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{productId}")
    @Operation(summary = "[관리자] 상품 수정", description = "관리자가 상품을 수정하는 API")
    public ResponseEntity<Void> updateProduct(@SessionAttribute(value = "loginMember", required = false) Member member,
                                              @PathVariable(name = "productId") Long productId,
                                              @Valid @RequestBody ProductUpdateRequest request) {
        productService.updateProduct(member, productId, request);
        return ResponseEntity.ok().build();
    }


    @DeleteMapping("/{productId}")
    @Operation(summary = "[관리자] 상품 삭제", description = "관리자가 상품을 삭제하는 API")
    public ResponseEntity<Void> deleteProduct(@SessionAttribute(value = "loginMember", required = false) Member member,
                                              @PathVariable(name = "productId") Long productId) {
        productService.deleteProduct(member, productId);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/category/{category}")
    @Operation(summary = "카테고리 별 상품 조회", description = "카테고리 별로 상품을 조회하는 API ")
    public ResponseEntity<List<Product>> readProductsByCategory(@PathVariable Category category) {
        return ResponseEntity.ok().body(productService.readProductsByCategory(category));
    }


    @PatchMapping("/{productId}")
    @Operation(summary = "[관리자] 상품 개별 할인", description = "관리자가 개별 상품을 할인하는 API. " +
            "parameter로 할인할 금액을 적어주세요.")
    public ResponseEntity<Void> discountProduct(@SessionAttribute(value = "loginMember", required = false) Member member,
                                                @PathVariable Long productId, @RequestParam("discount") int discount) {
        productService.discountProduct(member, productId, discount);
        return ResponseEntity.ok().build();
    }


    @PatchMapping("/category/{category}")
    @Operation(summary = "[관리자] 카테고리 상품 할인", description = "관리자가 카테고리의 상품을 할인하는 API. " +
            "parameter로 할인할 금액을 적어주세요.")
    public ResponseEntity<Void> discountCategoryProduct(@SessionAttribute(value = "loginMember", required = false) Member member,
                                                        @PathVariable Category category, @RequestParam("discount") int discount) {
        productService.discountCategoryProduct(member, category, discount);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/pop")
    @Operation(summary = "주문량 순 인기 상품 Top 10 조회", description = "인기 상품을 10개까지 조회하는 API. " +
            "주문량 내림차순으로 조회됩니다.")
    public ResponseEntity<List<Product>> readTop10Products() {
        return ResponseEntity.ok().body(productService.readTop10Products());
    }
}

