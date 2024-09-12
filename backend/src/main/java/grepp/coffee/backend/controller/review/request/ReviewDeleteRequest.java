package grepp.coffee.backend.controller.review.request;


import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewDeleteRequest {

    @NotNull
    Long memberId;

    @Builder
    public ReviewDeleteRequest(Long memberId) {
        this.memberId = memberId;
    }
}
