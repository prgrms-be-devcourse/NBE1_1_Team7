package grepp.coffee.backend.controller.product;


import grepp.coffee.backend.common.exception.ExceptionMessage;
import grepp.coffee.backend.common.exception.member.MemberException;
import grepp.coffee.backend.controller.product.request.ProductDetailResponse;
import grepp.coffee.backend.controller.product.request.ProductRegisterRequest;
import grepp.coffee.backend.controller.product.request.ProductUpdateRequest;
import grepp.coffee.backend.model.entity.member.Member;
import grepp.coffee.backend.model.entity.product.Product;
import grepp.coffee.backend.model.entity.product.constant.Category;
import grepp.coffee.backend.model.service.product.ProductService;
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
public class ProductController {

    private final ProductService productService;

    // 상품 전체 조회
    @GetMapping("")
    public ResponseEntity<List<Product>> readProductList() {
        return ResponseEntity.ok().body(productService.readProductList());
    }

    // 상품 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductDetails(@PathVariable("id") Long productId, HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("loginMember") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ExceptionMessage.MEMBER_NOT_LOGIN.getText());
        }

        ProductDetailResponse productDetailDTO = productService.getProductDetails(productId);
        return ResponseEntity.ok(productDetailDTO);
    }

    // 상품 등록
    @PostMapping("/")
    public ResponseEntity<Void> registerProduct(@SessionAttribute(value = "loginMember", required = false) Member member,
                                                @Valid @RequestBody ProductRegisterRequest request) {
        productService.registerProduct(member, request);
        return ResponseEntity.ok().build();
    }

    // 상품 수정
    @PutMapping("/{productId}")
    public ResponseEntity<Void> updateProduct(@SessionAttribute(value = "loginMember", required = false) Member member,
                                              @PathVariable(name = "productId") Long productId,
                                              @Valid @RequestBody ProductUpdateRequest request) {
        productService.updateProduct(member, productId, request);
        return ResponseEntity.ok().build();
    }

    // 상품 삭제
    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@SessionAttribute(value = "loginMember", required = false) Member member,
                                              @PathVariable(name = "productId") Long productId) {
        productService.deleteProduct(member, productId);
        return ResponseEntity.ok().build();
    }

    // 카테고리별 상품 조회
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Product>> readProductsByCategory(@PathVariable Category category) {
        return ResponseEntity.ok().body(productService.readProductsByCategory(category));
    }

    //상품 할인 적용
    @PatchMapping("/{productId}")
    public ResponseEntity<Void> discountProduct(@SessionAttribute(value = "loginMember", required = false) Member member,
                                                @PathVariable Long productId, @RequestParam("discount") int discount) {
        productService.discountProduct(member, productId, discount);
        return ResponseEntity.ok().build();
    }

    //카테고리별 상품 할인 적용
    @PatchMapping("/category/{category}")
    public ResponseEntity<Void> discountCategoryProduct(@SessionAttribute(value = "loginMember", required = false) Member member,
                                                        @PathVariable Category category, @RequestParam("discount") int discount) {
        productService.discountCategoryProduct(member, category, discount);
        return ResponseEntity.ok().build();
    }

    //인기 상품 목록 조회
    @GetMapping("/pop")
    public ResponseEntity<List<Product>> readTop10Products() {
        return ResponseEntity.ok().body(productService.readTop10Products());
    }
}

