package shopping.main.millions.repository.order;

import org.springframework.data.jpa.repository.JpaRepository;
import shopping.main.millions.entity.order.UserOrderEntity;

public interface UserOrderRepository extends JpaRepository<UserOrderEntity, Long> {
}
