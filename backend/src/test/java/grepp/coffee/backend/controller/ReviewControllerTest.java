package grepp.coffee.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import grepp.coffee.backend.MockTestConfig;
import grepp.coffee.backend.controller.review.request.ReviewDeleteRequest;
import grepp.coffee.backend.controller.review.request.ReviewRegisterRequest;
import grepp.coffee.backend.controller.review.request.ReviewUpdateRequest;
import grepp.coffee.backend.model.entity.member.Member;
import grepp.coffee.backend.model.entity.member.MemberFixture;
import grepp.coffee.backend.model.entity.product.Product;
import grepp.coffee.backend.model.entity.product.ProductFixture;
import grepp.coffee.backend.model.entity.review.Review;
import grepp.coffee.backend.model.entity.review.ReviewFixture;
import grepp.coffee.backend.model.repository.member.MemberRepository;
import grepp.coffee.backend.model.repository.product.ProductRepository;
import grepp.coffee.backend.model.repository.review.ReviewRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SuppressWarnings("NonAsciiCharacters")
public class ReviewControllerTest extends MockTestConfig {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ReviewRepository reviewRepository;


    @AfterEach
        // 테스트 종료후 db 초기화
    void tearDown() {
        reviewRepository.deleteAllInBatch();
        memberRepository.deleteAllInBatch();
        productRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("리뷰 등록 테스트")
    void reviewRegisterTest() throws Exception {
        // given
        Member member = MemberFixture.registerMember();
        memberRepository.save(member);

        Product product = ProductFixture.registerProduct();
        productRepository.save(product);

        ReviewRegisterRequest request = ReviewFixture.createReviewRequest(product.getProductId(), member.getMemberId());

        // when
        mockMvc.perform(post("/review/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                // then
                .andExpect(status().isOk());
    }


    @Test
    @DisplayName("리뷰 수정 테스트")
    void reviewUpdateTest() throws Exception {
        // given
        Member member = MemberFixture.registerMember();
        memberRepository.save(member);

        Product product = ProductFixture.registerProduct();
        productRepository.save(product);

        Review review = ReviewFixture.createReview(member, product.getProductId());
        reviewRepository.save(review);

        ReviewUpdateRequest request = ReviewFixture.createReviewUpdateRequest(product.getProductId(), member.getMemberId());


        // when
        mockMvc.perform(put("/review/" + review.getReviewId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                // then
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("리뷰 조회 테스트")
    void readReviewTest() throws Exception {

        // given
        Product product = ProductFixture.registerProduct();
        productRepository.save(product);

        // when
        mockMvc.perform(get("/review/" + product.getProductId())
                        .contentType(MediaType.APPLICATION_JSON))
                // then
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("리뷰 삭제 테스트")
    void deleteReviewTest() throws Exception {

        // given
        Member member = MemberFixture.registerMember();
        memberRepository.save(member);

        Product product = ProductFixture.registerProduct();
        productRepository.save(product);

        Review review = ReviewFixture.createReview(member, product.getProductId());
        reviewRepository.save(review);

        ReviewDeleteRequest request = ReviewFixture.createReviewDeleteRequest(member.getMemberId());


        // when
        mockMvc.perform(delete("/review/" + review.getReviewId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                // then
                .andExpect(status().isOk());
    }
}
