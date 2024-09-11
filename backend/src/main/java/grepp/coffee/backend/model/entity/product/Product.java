package grepp.coffee.backend.model.entity.product;

import grepp.coffee.backend.model.entity.BaseEntity;
import grepp.coffee.backend.model.entity.product.constant.Category;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 외부에서 객체 생성 못하도록 제한
@Entity(name = "PRODUCTS")
@Table(name = "PRODUCTS")
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_ID", nullable = false)
    private Long productId;

    @Column(name = "PRODUCT_NAME", length = 20, nullable = false)
    private String productName;

    @Enumerated(EnumType.STRING)
    @Column(name = "CATEGORY", length = 50, nullable = false)
    private Category category;

    @Column(name = "PRICE", nullable = false)
    private int price;

    @Column(name = "DESCRIPTION", length = 500)
    private String description;

    @Column(name = "ORDER_COUNT", nullable = false)
    private int orderCount;

    @Column(name = "DISCOUNT")
    private int discount;

    @Builder
    public Product(Long productId, String productName, Category category, int price, String description, int orderCount, int discount) {
        this.productId = productId;
        this.productName = productName;
        this.category = category;
        this.price = price;
        this.description = description;
        this.orderCount = orderCount;
        this.discount = discount;
    }

    public void updateProduct(String productName, Category category, int price, String description, int discount) {
        this.productName = productName;
        this.category = category;
        this.price = price;
        this.description = description;
        this.discount = discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }
}