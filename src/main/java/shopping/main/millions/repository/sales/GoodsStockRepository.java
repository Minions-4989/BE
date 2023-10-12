package shopping.main.millions.repository.sales;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import shopping.main.millions.entity.product.GoodsStockEntity;
import shopping.main.millions.entity.product.ProductEntity;

import java.util.List;
import java.util.Optional;

public interface GoodsStockRepository extends JpaRepository<GoodsStockEntity,Long> {
    Optional<GoodsStockEntity> findByStockSizeAndStockColorAndProductEntity_ProductId (String size, String color, Long productId);
    List<GoodsStockEntity> findAllByProductEntity_ProductId(Long productId);

    @Modifying
    @Transactional
    @Query("UPDATE GoodsStockEntity g SET g.stockQuantity = 0 WHERE g.productEntity.productId = :productId")
    void updateStockQuantityToZeroByProductId(Long productId);
}
