package grepp.coffee.backend.model.entity.product;

import grepp.coffee.backend.model.entity.product.constant.Category;

public class ProductFixture {

    // 테스트용 상품 등록
    public static Product registerProduct() {
        return Product.builder()
                .productName("상품이름")
                .category(Category.COFFEE)
                .price(1)
                .orderCount(1)
                .build();
    }
}
