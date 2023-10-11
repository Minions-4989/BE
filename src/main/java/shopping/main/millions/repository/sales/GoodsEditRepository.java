package shopping.main.millions.repository.sales;

import org.springframework.data.jpa.repository.JpaRepository;
import shopping.main.millions.entity.product.ProductEntity;

public interface GoodsEditRepository extends JpaRepository<ProductEntity,Long > {


}
