package grepp.coffee.backend.model.repository.product;


import grepp.coffee.backend.model.entity.product.Product;
import grepp.coffee.backend.model.entity.product.constant.Category;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    // 가격 기준으로 오름차순 정렬된 상품 조회
    @Query("SELECT p FROM PRODUCTS p WHERE p.price BETWEEN :minPrice AND :maxPrice ORDER BY p.price ASC")
    List<Product> findByPriceBetweenOrderedAsc(@Param("minPrice") int minPrice, @Param("maxPrice") int maxPrice);

    // 가격 기준으로 내림차순 정렬된 상품 조회
    @Query("SELECT p FROM PRODUCTS p WHERE p.price BETWEEN :minPrice AND :maxPrice ORDER BY p.price DESC")
    List<Product> findByPriceBetweenOrderedDesc(@Param("minPrice") int minPrice, @Param("maxPrice") int maxPrice);

    // 별점순에 따른 정렬
    @Query("SELECT p FROM PRODUCTS p JOIN REVIEW r ON p.productId = r.productId GROUP BY p.productId ORDER BY AVG(r.rating) ASC")
    List<Product> findAllOrderedByRatingAsc();

    @Query("SELECT p FROM PRODUCTS p JOIN REVIEW r ON p.productId = r.productId GROUP BY p.productId ORDER BY AVG(r.rating) DESC")
    List<Product> findAllOrderedByRatingDesc();


    // 각자의 기준으로 정렬된 상품 검색 (정렬 방향에 따라) [가격, 주문량, 이름] 사용중
    List<Product> findAll(Sort sort);






}



