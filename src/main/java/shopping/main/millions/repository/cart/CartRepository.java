package shopping.main.millions.repository.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import shopping.main.millions.entity.cart.CartEntity;

public interface CartRepository extends JpaRepository<CartEntity, Long> {

    Optional<CartEntity> findCartEntityByMemberEntity_UserId(Long userId);
}
