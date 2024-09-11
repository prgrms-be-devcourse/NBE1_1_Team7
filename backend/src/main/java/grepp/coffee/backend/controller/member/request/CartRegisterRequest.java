package grepp.coffee.backend.controller.member.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CartRegisterRequest {

    @NotNull
    private Long memberId;

    @NotNull
    private Long productId;

    @NotNull
    private int quantity;
}
