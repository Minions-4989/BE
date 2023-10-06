package shopping.main.millions.repository.sales;

import org.springframework.data.jpa.repository.JpaRepository;
import shopping.main.millions.entity.product.GoodsStockEntity;
import shopping.main.millions.entity.product.ProductEntity;

import java.util.Optional;

public interface GoodsStockRepository extends JpaRepository<GoodsStockEntity,Long> {
    Optional<GoodsStockEntity> findByStockSizeAndStockColorAndProductEntity_ProductId (String size, String color, Long productId);
}
