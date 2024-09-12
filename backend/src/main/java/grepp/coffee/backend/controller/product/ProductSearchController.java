package grepp.coffee.backend.controller.product;

import grepp.coffee.backend.model.entity.product.Product;
import grepp.coffee.backend.model.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class ProductSearchController {

    private final ProductService productService;

    // 가격 기준으로 오름차순 정렬된 상품 검색
    @GetMapping("/price/asc")
    public List<Product> searchProductsByPriceAsc(
            @RequestParam int minPrice,
            @RequestParam int maxPrice) {
        return productService.searchProductsByPriceAsc(minPrice, maxPrice);
    }

    // 가격 기준으로 내림차순 정렬된 상품 검색
    @GetMapping("/price/desc")
    public List<Product> searchProductsByPriceDesc(
            @RequestParam int minPrice,
            @RequestParam int maxPrice) {
        return productService.searchProductsByPriceDesc(minPrice, maxPrice);
    }

    // 가격없이 정렬 기준에 따라서만 정렬 true -> 오름차순 || false -> 내림차순
    @GetMapping("/price")
    public List<Product> searchProductsByPrice(
            @RequestParam(defaultValue = "true") boolean ascending) {
        return productService.searchProductsByPrice(ascending);
    }

    // 주문량에 따라 정렬 true -> 오름차순 || false -> 내림차순
    @GetMapping("/order-count")
    public List<Product> searchProductsByOrderCount(
            @RequestParam(defaultValue = "true") boolean ascending) {
        return productService.searchProductsByOrderCount(ascending);
    }

    // 이름순으로 정렬 true -> 오름차순 || false -> 내림차순
    @GetMapping("/name")
    public List<Product> searchProductsByName(
            @RequestParam(defaultValue = "true") boolean ascending) {
        return productService.searchProductsByName(ascending);
    }

    // 별점 평균 순으로 정렬 true -> 오름차순 || false -> 내림차순
    @GetMapping("/rating")
    public List<Product> searchProductsByRating(
            @RequestParam(defaultValue = "true") boolean ascending) {
        return productService.searchProductsByRating(ascending);
    }
}
