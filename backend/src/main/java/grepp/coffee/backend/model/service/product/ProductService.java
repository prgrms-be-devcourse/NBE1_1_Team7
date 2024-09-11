package grepp.coffee.backend.model.service.product;

import grepp.coffee.backend.common.exception.ExceptionMessage;
import grepp.coffee.backend.common.exception.product.ProductException;
import grepp.coffee.backend.controller.product.request.ProductRegisterRequest;
import grepp.coffee.backend.controller.product.request.ProductUpdateRequest;
import grepp.coffee.backend.model.entity.product.Product;
import grepp.coffee.backend.model.entity.product.constant.Category;
import grepp.coffee.backend.model.repository.product.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    // 상품 전체 조회
    public List<Product> readProductList() {
        return productRepository.findAll();
    }

    // 상품 등록
    @Transactional
    public void registerProduct(ProductRegisterRequest request) {

        // Product 엔티티에 추가된 orderCount와 discount 반영
        Product product = Product.builder()
                .productName(request.getProductName())
                .category(request.getCategory())
                .price(request.getPrice())
                .description(request.getDescription())
                .orderCount(0)
                .discount(request.getDiscount())
                .build();
        productRepository.save(product);
    }

    // 상품 수정
    @Transactional
    public void updateProduct(Long productId, ProductUpdateRequest request) {
        // 상품 조회
        Product product = findByIdOrThrowProductException(productId);

        // 수정된 정보를 반영
        product.updateProduct(
                request.getProductName(),
                request.getCategory(),
                request.getPrice(),
                request.getDescription(),
                request.getDiscount()
        );
    }

    // 상품 삭제
    @Transactional
    public void deleteProduct(Long productId) {
        Product product = findByIdOrThrowProductException(productId);
        productRepository.delete(product);
    }

    // 카테고리별 상품 조회
    public List<Product> readProductsByCategory(Category category) {
        return productRepository.findByCategory(category);
    }

    //상품 할인
    @Transactional
    public void discountProduct(Long productId, int discount) {
        Product product = findByIdOrThrowProductException(productId);
        product.setDiscount(discount);
    }

    // 상품 조회 예외처리
    public Product findByIdOrThrowProductException(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> {
                    log.warn(">>>> {} : {} <<<<", productId, ExceptionMessage.PRODUCT_NOT_FOUND);
                    return new ProductException(ExceptionMessage.PRODUCT_NOT_FOUND);
                });
    }
}
