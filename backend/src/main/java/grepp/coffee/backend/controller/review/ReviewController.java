package grepp.coffee.backend.controller.review;


import grepp.coffee.backend.controller.review.request.ReviewDeleteRequest;
import grepp.coffee.backend.controller.review.request.ReviewRegisterRequest;
import grepp.coffee.backend.controller.review.request.ReviewUpdateRequest;
import grepp.coffee.backend.model.entity.review.Review;
import grepp.coffee.backend.model.service.review.ReviewService;
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
public class ReviewController {

    private final ReviewService reviewService;


    // 리뷰 등록
    @PostMapping("/")
    public ResponseEntity<Void> registerReview(@Valid @RequestBody ReviewRegisterRequest request) {

        reviewService.registerReview(request);
        return ResponseEntity.ok().build();
    }

    // 리뷰 수정
    @PutMapping("/{reviewId}")
    public ResponseEntity<Void> updateReview(@PathVariable(name = "reviewId") Long reviewId,
                                             @Valid @RequestBody ReviewUpdateRequest request) {

        reviewService.updateReview(reviewId, request);
        return ResponseEntity.ok().build();
    }

    // 리뷰 조회
    @GetMapping("/{productId}")
    public ResponseEntity<List<Review>> readReviewList(@PathVariable(name = "productId") Long productId) {

        return ResponseEntity.ok().body(reviewService.readReviewList(productId));
    }

    // 리뷰 삭제
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable(name = "reviewId") Long reviewId,
                                             @Valid @RequestBody ReviewDeleteRequest request) {

        reviewService.deleteReview(reviewId, request);
        return ResponseEntity.ok().build();
    }
}
