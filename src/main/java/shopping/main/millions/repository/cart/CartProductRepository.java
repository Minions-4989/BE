package shopping.main.millions.repository.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import shopping.main.millions.entity.cart.CartProductEntity;

public interface CartProductRepository extends JpaRepository<CartProductEntity,Long> {
}
