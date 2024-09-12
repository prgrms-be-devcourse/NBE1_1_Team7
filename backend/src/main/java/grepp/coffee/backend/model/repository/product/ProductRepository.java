package grepp.coffee.backend.model.repository.product;


import grepp.coffee.backend.model.entity.product.Product;
import grepp.coffee.backend.model.entity.product.constant.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {


    // 카테고리별로 상품을 조회하는 메서드
    List<Product> findByCategory(Category category);

    //주문량 내림차순으로 인기 상품을 조회하는 메서드
    //jpa 네이밍 규칙에 Order가 들어갈 경우 정상 작동하지 않으므로 JPQL 사용
    @Query(value = "SELECT p FROM PRODUCTS p ORDER BY p.orderCount DESC LIMIT 10")
    List<Product> findTop10ByOrderCountDesc();
}
