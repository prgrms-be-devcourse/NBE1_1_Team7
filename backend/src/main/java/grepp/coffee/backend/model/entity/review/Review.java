package grepp.coffee.backend.model.entity.review;

import grepp.coffee.backend.model.entity.BaseEntity;
import grepp.coffee.backend.model.entity.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "REVIEW")
@Table(name = "REVIEW")
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REVIEW_ID")
    private Long reviewId;

    @Column(name = "PRODUCT_ID", nullable = false)
    private Long productId;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID", nullable = false)
    private Member member;

    @Column(name = "REVIEW", length = 200)
    private String review;

    @Column(name = "RATING", nullable = false)
    private int rating;

    @Builder
    public Review(Long productId, Member member, String review, int rating) {
        this.productId = productId;
        this.member = member;
        this.review = review;
        this.rating = rating;
    }
}
