package grepp.coffee.backend.controller.product;


import grepp.coffee.backend.controller.product.request.ProductRegisterRequest;
import grepp.coffee.backend.controller.product.request.ProductUpdateRequest;
import grepp.coffee.backend.model.entity.product.Product;
import grepp.coffee.backend.model.entity.product.constant.Category;
import grepp.coffee.backend.model.service.product.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
}

