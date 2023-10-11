package shopping.main.millions.repository.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import shopping.main.millions.entity.cart.CartProductEntity;
import shopping.main.millions.entity.product.ProductEntity;

import java.util.List;
import java.util.Optional;

public interface CartProductRepository extends JpaRepository<CartProductEntity,Long> {

    Optional<List<CartProductEntity>> findCartProductEntityByCartEntity_CartId(Long cartId);

    Optional<CartProductEntity> findCartProductEntityByCartProductColorAndCartProductSizeAndProductEntity_ProductIdAndCartEntity_CartId(
            String color, String size, Long productId, Long cartId
    );

    @Transactional
    @Modifying
    @Query("update CartProductEntity p set p.cartProductCount = p.cartProductCount + :count where p.cartProductId = :id")
    void updateCartProductCount(@Param("count") Long count, @Param("id") Long id);

}
