package shopping.main.millions.repository.sales;

import org.springframework.data.jpa.repository.JpaRepository;
import shopping.main.millions.entity.product.GoodsImageEntity;

import java.util.List;

public interface GoodsImageRepository extends JpaRepository<GoodsImageEntity,Long> {

        List<GoodsImageEntity> findByImageId(Long imageId);

        List<GoodsImageEntity> findByProductEntityProductId(Long productId);
}
