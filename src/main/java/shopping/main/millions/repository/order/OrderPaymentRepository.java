package shopping.main.millions.repository.order;

import org.springframework.data.jpa.repository.JpaRepository;
import shopping.main.millions.entity.order.OrderPaymentEntity;

public interface OrderPaymentRepository extends JpaRepository<OrderPaymentEntity, Long> {
}
