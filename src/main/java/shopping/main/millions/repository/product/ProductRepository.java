package shopping.main.millions.repository.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import shopping.main.millions.entity.product.ProductEntity;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity,Long> {

    Page<ProductEntity> findAllByCategoryEntityOrderByProductId (Long categoryId, Pageable pageable);
}
