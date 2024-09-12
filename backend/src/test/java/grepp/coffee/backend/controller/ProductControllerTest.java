package grepp.coffee.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import grepp.coffee.backend.model.entity.product.Product;
import grepp.coffee.backend.model.entity.product.ProductFixture;
import grepp.coffee.backend.model.entity.product.constant.Category;
import grepp.coffee.backend.model.service.product.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Product Controller의")
@SuppressWarnings("NonAsciiCharacters")
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    @Test
    @DisplayName("상품 개별 할인 API 테스트")
    //TODO : 멤버 기능 개발 완료 후 관리자 권한 체크
    public void discountProductTest() throws Exception {
        //given
        long productId = 1L;

        //when
        mockMvc.perform(patch("/product/{productId}", productId)
                        .param("discount", "1000")
                        .contentType(MediaType.APPLICATION_JSON))
                // then
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("상품 카테고리별 할인 API 테스트")
    //TODO : 멤버 기능 개발 완료 후 관리자 권한 체크
    public void discountCategoryProductTest() throws Exception {
        //when
        mockMvc.perform(patch("/product/category/{category}", Category.COFFEE)
                        .param("discount", "1000")
                        .contentType(MediaType.APPLICATION_JSON))
                // then
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("상품 인기 조회 테스트")
    public void readTop10Products() throws Exception {
        //when
        mockMvc.perform(get("/product/pop")
                        .param("discount", "1000")
                        .contentType(MediaType.APPLICATION_JSON))
                // then
                .andExpect(status().isOk());
    }
}
