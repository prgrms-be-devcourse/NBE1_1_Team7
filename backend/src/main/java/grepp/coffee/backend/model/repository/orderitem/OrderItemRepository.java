package grepp.coffee.backend.model.repository.orderitem;


import grepp.coffee.backend.model.entity.order.Order;
import grepp.coffee.backend.model.entity.orderitem.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {


    List<OrderItem> findByOrder(Order order);
}