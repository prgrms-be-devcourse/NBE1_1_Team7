package grepp.coffee.backend.controller.review;


import grepp.coffee.backend.controller.review.request.ReviewDeleteRequest;
import grepp.coffee.backend.controller.review.request.ReviewRegisterRequest;
import grepp.coffee.backend.controller.review.request.ReviewUpdateRequest;
import grepp.coffee.backend.model.entity.review.Review;
import grepp.coffee.backend.model.service.review.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/review")
@RestController
@Tag(name = "상품 리뷰 API")
public class ReviewController {

    private final ReviewService reviewService;



    @PostMapping("/")
    @Operation(summary = "리뷰 등록", description = "제품의 리뷰를 등록하는 API")
    public ResponseEntity<Void> registerReview(@Valid @RequestBody ReviewRegisterRequest request) {

        reviewService.registerReview(request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{reviewId}")
    @Operation(summary = "리뷰 수정", description = "본인이 작성한 제품의 리뷰를 수정하는 API")
    public ResponseEntity<Void> updateReview(@PathVariable(name = "reviewId") Long reviewId,
                                             @Valid @RequestBody ReviewUpdateRequest request) {

        reviewService.updateReview(reviewId, request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{productId}")
    @Operation(summary = "리뷰 목록 조회", description = "상품의 리뷰 목록을 조회하는 API")
    public ResponseEntity<List<Review>> readReviewList(@PathVariable(name = "productId") Long productId) {

        return ResponseEntity.ok().body(reviewService.readReviewList(productId));
    }

    @DeleteMapping("/{reviewId}")
    @Operation(summary = "리뷰 삭제", description = "본인이 작성한 제품의 리뷰를 삭제하는 API")
    public ResponseEntity<Void> deleteReview(@PathVariable(name = "reviewId") Long reviewId,
                                             @Valid @RequestBody ReviewDeleteRequest request) {

        reviewService.deleteReview(reviewId, request);
        return ResponseEntity.ok().build();
    }
}
