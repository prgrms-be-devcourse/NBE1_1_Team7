package grepp.coffee.backend.model.repository.review;

import grepp.coffee.backend.model.entity.review.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByProductId(Long productId);

    @Query("SELECT AVG(r.rating) FROM REVIEW r WHERE r.productId = :productId")
    Double findAverageRatingByProductId(@Param("productId") Long productId);
}
