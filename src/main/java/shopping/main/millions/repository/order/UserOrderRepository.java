package shopping.main.millions.repository.order;

import org.springframework.data.jpa.repository.JpaRepository;
import shopping.main.millions.entity.order.UserOrderEntity;

import java.util.List;

public interface UserOrderRepository extends JpaRepository<UserOrderEntity, Long> {
    List<UserOrderEntity> findByCartEntity_CartId(Long cartId);
}
