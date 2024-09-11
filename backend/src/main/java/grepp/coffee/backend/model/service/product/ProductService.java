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

        //할인 가격이 원래 금액보다 클 경우를 검사
        validateDiscount(product, discount);
        product.setDiscount(discount);
    }

    //카테고리로 상품 할인
    @Transactional
    public void discountCategoryProduct(Category category, int discount) {
        List<Product> categoryProducts = productRepository.findByCategory(category);
        categoryProducts.forEach(product -> {
            //할인 가격이 원래 금액보다 클 경우를 검사
            validateDiscount(product, discount);
            product.setDiscount(discount);
        });
    }

    //인기 상품 목록 조회
    public List<Product> readTop10Products() {
        return productRepository.findTop10ByOrderCountDesc();
    }

    private void validateDiscount(Product product, int discount) {
        if (product.getPrice() < discount) {
            throw new ProductException(ExceptionMessage.PRODUCT_DISCOUNT_BAE_REQUEST);
        }
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
