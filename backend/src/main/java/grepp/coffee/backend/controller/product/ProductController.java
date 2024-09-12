package grepp.coffee.backend.controller.product;


import grepp.coffee.backend.controller.product.request.ProductDetailResponse;
import grepp.coffee.backend.controller.product.request.ProductRegisterRequest;
import grepp.coffee.backend.controller.product.request.ProductUpdateRequest;
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
    public ResponseEntity<ProductDetailResponse> getProductDetails(@PathVariable("id") Long productId, HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("loginMember") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        ProductDetailResponse productDetailDTO = productService.getProductDetails(productId);
        return ResponseEntity.ok(productDetailDTO);
    }

    // 가격 기준으로 오름차순 정렬된 상품 검색
    @GetMapping("/search/price/asc")
    public List<Product> searchProductsByPriceAsc(
            @RequestParam int minPrice,
            @RequestParam int maxPrice) {
        return productService.searchProductsByPriceAsc(minPrice, maxPrice);
    }

    // 가격 기준으로 내림차순 정렬된 상품 검색
    @GetMapping("/search/price/desc")
    public List<Product> searchProductsByPriceDesc(
            @RequestParam int minPrice,
            @RequestParam int maxPrice) {
        return productService.searchProductsByPriceDesc(minPrice, maxPrice);
    }

    // 가격없이 정렬 기준에 따라서만 정렬 true -> 오름차순 || false -> 내림차순
    @GetMapping("/search/price")
    public List<Product> searchProductsByPrice(
            @RequestParam(defaultValue = "true") boolean ascending) {
        return productService.searchProductsByPrice(ascending);
    }

    // 주문량에 따라 정렬 true -> 오름차순 || false -> 내림차순
    @GetMapping("/search/order-count")
    public List<Product> searchProductsByOrderCount(
            @RequestParam(defaultValue = "true") boolean ascending) {
        return productService.searchProductsByOrderCount(ascending);
    }

    // 이름순으로 정렬 true -> 오름차순 || false -> 내림차순
    @GetMapping("/search/name")
    public List<Product> searchProductsByName(
            @RequestParam(defaultValue = "true") boolean ascending) {
        return productService.searchProductsByName(ascending);
    }

    // 별점 평균 순으로 정렬 true -> 오름차순 || false -> 내림차순
    @GetMapping("/search/rating")
    public List<Product> searchProductsByRating(
            @RequestParam(defaultValue = "true") boolean ascending) {
        return productService.searchProductsByRating(ascending);
    }

    // 상품 등록
    @PostMapping("/")
    public ResponseEntity<Void> registerProduct(@Valid @RequestBody ProductRegisterRequest request) {
        productService.registerProduct(request);
        return ResponseEntity.ok().build();
    }

    // 상품 수정
    @PutMapping("/{productId}")
    public ResponseEntity<Void> updateProduct(@PathVariable(name = "productId") Long productId,
                                              @Valid @RequestBody ProductUpdateRequest request) {
        productService.updateProduct(productId, request);
        return ResponseEntity.ok().build();
    }

    // 상품 삭제
    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable(name = "productId") Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.ok().build();
    }

    // 카테고리별 상품 조회
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Product>> readProductsByCategory(@PathVariable Category category) {
        return ResponseEntity.ok().body(productService.readProductsByCategory(category));
    }

    //상품 할인 적용
    @PatchMapping("/{productId}")
    public ResponseEntity<Void> discountProduct(@PathVariable Long productId, @RequestParam("discount") int discount) {
        productService.discountProduct(productId, discount);
        return ResponseEntity.ok().build();
    }

    //카테고리별 상품 할인 적용
    @PatchMapping("/category/{category}")
    public ResponseEntity<Void> discountCategoryProduct(@PathVariable Category category, @RequestParam("discount") int discount) {
        productService.discountCategoryProduct(category, discount);
        return ResponseEntity.ok().build();
    }

    //인기 상품 목록 조회
    @GetMapping("/pop")
    public ResponseEntity<List<Product>> readTop10Products() {
        return ResponseEntity.ok().body(productService.readTop10Products());
    }
}

