package grepp.coffee.backend.model.service.review;

import grepp.coffee.backend.common.exception.ExceptionMessage;
import grepp.coffee.backend.common.exception.review.ReviewException;
import grepp.coffee.backend.controller.review.request.ReviewRegisterRequest;
import grepp.coffee.backend.controller.review.request.ReviewUpdateRequest;
import grepp.coffee.backend.model.entity.member.Member;
import grepp.coffee.backend.model.entity.product.Product;
import grepp.coffee.backend.model.entity.review.Review;
import grepp.coffee.backend.model.repository.review.ReviewRepository;
import grepp.coffee.backend.model.service.member.MemberService;
import grepp.coffee.backend.model.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductService productService;
    private final MemberService memberService;

    // 리뷰 등록
    @Transactional
    public void registerReview(ReviewRegisterRequest request) {

        // 사용자 조회
        Member member = memberService.findByIdOrThrowMemberException(request.getMemberId());

        // 상품 조회
        Product product = productService.findByIdOrThrowProductException(request.getProductId());

        Review review = Review.builder()
                .productId(product.getProductId())
                .member(member)
                .review(request.getReview())
                .rating(0) // 기본 할인율 0
                .build();
        reviewRepository.save(review);
    }

    // 리뷰 수정
    @Transactional
    public void updateReview(Long reviewId, ReviewUpdateRequest request) {

        // 리뷰 조회
        Review review = findByIdOrThrowReviewException(reviewId);

        // 사용자 조회
        memberService.findByIdOrThrowMemberException(request.getMemberId());

        // 상품 조회
        productService.findByIdOrThrowProductException(request.getProductId());

        review.updateReview(
                request.getReview()
        );

    }

    // 특정 상품 리뷰리스트 조회
    public List<Review> readReviewList(Long productId) {

        // 상품 조회
        Product product = productService.findByIdOrThrowProductException(productId);


        return reviewRepository.findByProductId(product.getProductId());
    }


    // 리뷰 조회 예외처리
    public Review findByIdOrThrowReviewException(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> {
                    log.warn(">>>> {} : {} <<<<", reviewId, ExceptionMessage.REVIEW_NOT_FOUND);
                    return new ReviewException(ExceptionMessage.REVIEW_NOT_FOUND);
                });
    }
}




