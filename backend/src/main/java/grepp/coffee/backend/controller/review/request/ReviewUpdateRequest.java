package grepp.coffee.backend.controller.review.request;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewUpdateRequest {

    @NotNull
    private Long productId;

    @NotNull
    private Long memberId;

    @Size(max = 300, message = "리뷰 300자 이내")
    private String review;

    private int rating;

    @Builder
    public ReviewUpdateRequest(Long productId, Long memberId, String review, int rating) {
        this.productId=productId;
        this.memberId=memberId;
        this.review=review;
        this.rating = rating;
    }
}
