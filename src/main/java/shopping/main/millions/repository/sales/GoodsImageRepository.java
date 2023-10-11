package shopping.main.millions.repository.sales;

import org.springframework.data.jpa.repository.JpaRepository;
import shopping.main.millions.entity.product.GoodsImageEntity;



import java.util.List;
import java.util.Optional;

public interface GoodsImageRepository extends JpaRepository<GoodsImageEntity,Long> {
    Optional<List<GoodsImageEntity>> findGoodsImageEntitiesByProductEntity_ProductId(Long productId);

        List<GoodsImageEntity> findByImageId(Long imageId);

        List<GoodsImageEntity> findByProductEntityProductId(Long productId);
}
