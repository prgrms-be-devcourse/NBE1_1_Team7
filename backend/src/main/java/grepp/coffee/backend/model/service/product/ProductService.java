package grepp.coffee.backend.model.service.product;

import grepp.coffee.backend.common.exception.ExceptionMessage;
import grepp.coffee.backend.common.exception.member.MemberException;
import grepp.coffee.backend.common.exception.product.ProductException;
import grepp.coffee.backend.controller.product.request.ProductDetailResponse;
import grepp.coffee.backend.controller.product.request.ProductRegisterRequest;
import grepp.coffee.backend.controller.product.request.ProductUpdateRequest;
import grepp.coffee.backend.model.entity.member.Member;
import grepp.coffee.backend.model.entity.orderitem.OrderItem;
import grepp.coffee.backend.model.entity.product.Product;
import grepp.coffee.backend.model.entity.product.constant.Category;
import grepp.coffee.backend.model.repository.member.MemberRepository;
import grepp.coffee.backend.model.repository.product.ProductRepository;
import grepp.coffee.backend.model.repository.review.ReviewRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import static grepp.coffee.backend.common.exception.ExceptionMessage.ADMIN_ACCESS_ONLY;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Value("${admin.email}")
    private String ADMIN_EMAIL;

    // 상품 전체 조회
    public List<Product> readProductList() {
        return productRepository.findAll();
    }

    // 상품 상세 조회
    public ProductDetailResponse getProductDetails(Long productId) {
        Product product = findByIdOrThrowProductException(productId);


        return ProductDetailResponse.builder()
                .productName(product.getProductName())
                .category(product.getCategory())
                .price(product.getPrice())
                .description(product.getDescription())
                .discount(product.getDiscount())
                .build();
    }

    // 상품 등록
    @Transactional
    public void registerProduct(Member member, ProductRegisterRequest request) {
        //관리자 권한 체크
        validAdmin(member);

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

    // (범위가 지정된) 가격 기준으로 오름차순 정렬된 상품 검색
    public List<Product> searchProductsByPriceAsc(int minPrice, int maxPrice) {
        validatePriceRange(minPrice, maxPrice);
        return productRepository.findByPriceBetweenOrderedAsc(minPrice, maxPrice);
    }

    // (범위가 지정된) 가격 기준으로 내림차순 정렬된 상품 검색
    public List<Product> searchProductsByPriceDesc(int minPrice, int maxPrice) {
        validatePriceRange(minPrice, maxPrice);
        return productRepository.findByPriceBetweenOrderedDesc(minPrice, maxPrice);
    }

    //정렬 방향에 따라서 price를 기준으로 상품 검색 (정렬 방향에 따라)
    public List<Product> searchProductsByPrice(boolean ascending) {
        Sort sort = ascending ? Sort.by(Sort.Order.asc("price")) : Sort.by(Sort.Order.desc("price"));
        return productRepository.findAll(sort);
    }

    // 정렬 방향에 따라 orderCount를 기준으로 상품 검색 (주문량)
    public List<Product> searchProductsByOrderCount(boolean ascending) {
        Sort sort = ascending ? Sort.by(Sort.Order.asc("orderCount")) : Sort.by(Sort.Order.desc("orderCount"));
        return productRepository.findAll(sort);
    }

    // 정렬 방향에 따라 productName를 기준으로 상품 검색
    public List<Product> searchProductsByName(boolean ascending) {
        Sort sort = ascending ? Sort.by(Sort.Order.asc("productName")) : Sort.by(Sort.Order.desc("productName"));
        return productRepository.findAll(sort);
    }

    // 별점순에 따라 정렬
    public List<Product> searchProductsByRating(boolean ascending) {
        return ascending ? productRepository.findAllOrderedByRatingAsc() : productRepository.findAllOrderedByRatingDesc();
    }

    // 금액 범위 검증
    private void validatePriceRange(int minPrice, int maxPrice) {
        if (minPrice > maxPrice) {
            throw new ProductException(ExceptionMessage.PRODUCT_MIN_PRICE_BIGGER_THAN_MAX_PRICE);
        }
    }

    // 상품 수정
    @Transactional
    public void updateProduct(Member member, Long productId, ProductUpdateRequest request) {
        //관리자 권한 체크
        validAdmin(member);

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
    public void deleteProduct(Member member, Long productId) {
        //관리자 권한 체크
        validAdmin(member);

        Product product = findByIdOrThrowProductException(productId);
        productRepository.delete(product);
    }

    // 카테고리별 상품 조회
    public List<Product> readProductsByCategory(Category category) {
        return productRepository.findByCategory(category);
    }

    //상품 할인
    @Transactional
    public void discountProduct(Member loginMember, Long productId, int discount) {
        //관리자 권한 체크
        validAdmin(loginMember);

        Product product = findByIdOrThrowProductException(productId);

        //할인 가격이 원래 금액보다 클 경우를 검사
        validateDiscount(product, discount);
        product.setDiscount(discount);
    }

    //카테고리로 상품 할인
    @Transactional
    public void discountCategoryProduct(Member loginMember, Category category, int discount) {
        //관리자 권한 체크
        validAdmin(loginMember);

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

    //현재 관리자 Role이 없으므로 임의의 email check
    private void validAdmin(Member loginMember) {
        if (loginMember == null || !loginMember.getEmail().equals(ADMIN_EMAIL))
            throw new MemberException(ADMIN_ACCESS_ONLY);
    }

    public void decreaseProductOrderCount(List<OrderItem> orderItems) {
        orderItems.forEach(orderItem -> {
            Product product = orderItem.getProduct();
            product.decreaseOrderCount(orderItem.getQuantity());
        });
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
