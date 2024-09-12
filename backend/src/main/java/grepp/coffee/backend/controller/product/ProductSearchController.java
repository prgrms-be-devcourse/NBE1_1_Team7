package grepp.coffee.backend.controller.product;

import grepp.coffee.backend.model.entity.product.Product;
import grepp.coffee.backend.model.service.product.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
@Tag(name = "상품 목록 필터링 API")
public class ProductSearchController {

    private final ProductService productService;

    @GetMapping("/price/asc")
    @Operation(summary = "정해진 가격 내에서 오름차순 정렬 상품 목록 조회", description = "가격 오름차순으로 상품 목록을 조회하는 API" +
            "하한 가격과 상한 가격을 param으로 주세요.")
    public List<Product> searchProductsByPriceAsc(
            @RequestParam int minPrice,
            @RequestParam int maxPrice) {
        return productService.searchProductsByPriceAsc(minPrice, maxPrice);
    }


    @GetMapping("/price/desc")
    @Operation(summary = "정해진 가격 내에서 내림차순 정렬 상품 목록 조회", description = "가격 내림차순으로 상품 목록을 조회하는 API" +
            "하한 가격과 상한 가격을 param으로 주세요.")
    public List<Product> searchProductsByPriceDesc(
            @RequestParam int minPrice,
            @RequestParam int maxPrice) {
        return productService.searchProductsByPriceDesc(minPrice, maxPrice);
    }


    @GetMapping("/price")
    @Operation(summary = "가격을 기준으로 정렬하여 상품 목록 조회", description = "가격 오름차순, 내림차순으로 상품목록을 조회하는 API" +
            "param값이 true면 오름차순, false면 내림차순입니다.")
    public List<Product> searchProductsByPrice(
            @RequestParam(defaultValue = "true") boolean ascending) {
        return productService.searchProductsByPrice(ascending);
    }


    @GetMapping("/order-count")
    @Operation(summary = "주문량을 기준으로 정렬하여 상품 목록 조회", description = "주문량 오름차순, 내림차순으로 상품목록을 조회하는 API" +
            "param값이 true면 오름차순, false면 내림차순입니다.")
    public List<Product> searchProductsByOrderCount(
            @RequestParam(defaultValue = "true") boolean ascending) {
        return productService.searchProductsByOrderCount(ascending);
    }


    @GetMapping("/name")
    @Operation(summary = "이름을 기준으로 정렬하여 상품 목록 조회", description = "이름 오름차순, 내림차순으로 상품목록을 조회하는 API" +
            "param값이 true면 오름차순, false면 내림차순입니다.")
    public List<Product> searchProductsByName(
            @RequestParam(defaultValue = "true") boolean ascending) {
        return productService.searchProductsByName(ascending);
    }

    @GetMapping("/rating")
    @Operation(summary = "별점을 기준으로 정렬하여 상품 목록 조회", description = "별점 오름차순, 내림차순으로 상품목록을 조회하는 API" +
            "param값이 true면 오름차순, false면 내림차순입니다.")
    public List<Product> searchProductsByRating(
            @RequestParam(defaultValue = "true") boolean ascending) {
        return productService.searchProductsByRating(ascending);
    }
}
