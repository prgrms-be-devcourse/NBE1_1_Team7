package grepp.coffee.backend.controller.product.request;


import grepp.coffee.backend.model.entity.product.constant.Category;
import lombok.Builder;

@Builder
public class ProductDetailResponse {

    private String productName;
    private Category category;

    private int price;

    private String description;

    private int orderCount;

    private int discount;
}

