package grepp.coffee.backend.controller.order.request;

import grepp.coffee.backend.controller.orderitem.request.OrderItemRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderUpdateRequest {

    @NotNull
    private List<OrderItemRequest> updatedOrderItems;
}
