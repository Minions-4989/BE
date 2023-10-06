package shopping.main.millions.repository.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import shopping.main.millions.entity.cart.CartProductEntity;

import java.util.List;

public interface CartProductRepository extends JpaRepository<CartProductEntity,Long> {
    List<CartProductEntity> findCartProductEntityByMemberEntity_UserId(Long userId);
}
