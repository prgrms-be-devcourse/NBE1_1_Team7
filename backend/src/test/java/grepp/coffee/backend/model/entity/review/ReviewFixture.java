package grepp.coffee.backend.model.entity.review;

import grepp.coffee.backend.controller.review.request.ReviewRegisterRequest;
import grepp.coffee.backend.controller.review.request.ReviewUpdateRequest;
import grepp.coffee.backend.model.entity.member.Member;

public class ReviewFixture {

    // 테스트용 리뷰 생성
    public static Review createReview(Member member, Long prductId) {
        return Review.builder()
                .productId(prductId)
                .member(member)
                .review("테스트")
                .rating(0)
                .build();
    }

    // 테스트용 리뷰 등록 DTO 생성
    public static ReviewRegisterRequest createReviewRequest(Long productId, Long memberId) {
        return ReviewRegisterRequest.builder()
                .productId(productId)
                .memberId(memberId)
                .review("리뷰 테스트")
                .build();
    }

    // 테스트용 리뷰 수정 DTO 생성
    public static ReviewUpdateRequest createReviewUpdateRequest(Long productId, Long memberId) {
        return ReviewUpdateRequest.builder()
                .productId(productId)
                .memberId(memberId)
                .review("리뷰 수정 테스트")
                .build();
    }
}
