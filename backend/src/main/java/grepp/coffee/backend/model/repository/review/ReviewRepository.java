package grepp.coffee.backend.model.repository.review;

import grepp.coffee.backend.model.entity.review.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByProductId(Long productId);
}
