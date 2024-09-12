package grepp.coffee.backend.service;

import grepp.coffee.backend.common.exception.product.ProductException;
import grepp.coffee.backend.model.entity.product.Product;
import grepp.coffee.backend.model.entity.product.ProductFixture;
import grepp.coffee.backend.model.entity.product.constant.Category;
import grepp.coffee.backend.model.repository.product.ProductRepository;
import grepp.coffee.backend.model.service.product.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class ProductServiceTest {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("상품 개별 할인 등록 테스트")
    @Transactional
    public void discountProductTest() {
        //given
        Product product = ProductFixture.registerProduct();
        productRepository.save(product);
        //when
        productService.discountProduct(product.getProductId(), 1000);
        //then
        assertEquals(9000, product.getPrice() - product.getDiscount());
    }

    @Test
    @DisplayName("상품 카테고리별 할인 등록 테스트")
    @Transactional
    public void discountCategoryProductTest() {
        //given
        Product product = ProductFixture.registerProductWithCategory(Category.TEA);
        productRepository.save(product);
        //when
        productService.discountCategoryProduct(Category.TEA, 1000);
        //then
        assertEquals(9000, product.getPrice() - product.getDiscount());
    }

    @Test
    @DisplayName("상품 개별 할인 등록 실패 테스트 - productId를 찾을 수 없음")
    @Transactional
    public void discountProductNotFoundTest() {

        //when && then
        assertThrows(ProductException.class, () -> {
            productService.discountProduct(1012143L, 1000);
        });
    }

    @Test
    @DisplayName("상품 개별 할인 등록 실패 테스트 - 할인금액 > 기존 금액일 경우")
    @Transactional
    public void discountProductBadRequestTest() {
        //given
        Product product = ProductFixture.registerProduct();
        productRepository.save(product);
        //when && then
        assertThrows(ProductException.class, () -> {
            productService.discountProduct(product.getProductId(), 100000);
        });
    }

    @Test
    @DisplayName("상품 인기 조회 테스트")
    @Transactional
    public void readTop10Products() {
        //given
        for (int orderCount = 1; orderCount <= 15; orderCount++) {
            Product product = ProductFixture.registerProductWithOrderCount(orderCount); //orderCount 증가시켜 임의 생성
            productRepository.save(product);
        }

        //when
        List<Product> productList = productService.readTop10Products();
        assertEquals(10, productList.size());
        assertEquals(15, productList.get(0).getOrderCount());
    }
}
