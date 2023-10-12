package shopping.main.millions.repository.order;

import org.springframework.data.jpa.repository.JpaRepository;
import shopping.main.millions.entity.order.OrderEntity;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity,Long> {
    List<OrderEntity> findByUserOrderEntity_UserOrderId(Long userOrderId);
}
