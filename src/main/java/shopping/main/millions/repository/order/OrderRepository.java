package shopping.main.millions.repository.order;

import org.springframework.data.jpa.repository.JpaRepository;
import shopping.main.millions.entity.order.OrderEntity;

public interface OrderRepository extends JpaRepository<OrderEntity,Long> {
}
