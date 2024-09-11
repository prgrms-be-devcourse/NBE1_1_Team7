package grepp.coffee.backend.controller.product.request;

import grepp.coffee.backend.model.entity.product.constant.Category;

// 검색 조건을 위한 DTO


public class ProductSearchRequest {
    private Category category;
    private Integer minPrice;
    private Integer maxPrice;
    private Integer minOrderCount;
    private Integer maxOrderCount;
    private String productName;
    private Double minRating;
    private Double maxRating;
}
