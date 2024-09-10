package grepp.coffee.backend.model.repository.product;


import grepp.coffee.backend.model.entity.product.Product;
import grepp.coffee.backend.model.entity.product.constant.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {


    // 카테고리별로 상품을 조회하는 메서드
    List<Product> findByCategory(Category category);
}
