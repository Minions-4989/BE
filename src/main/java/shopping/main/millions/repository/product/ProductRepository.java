package shopping.main.millions.repository.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import shopping.main.millions.entity.product.ProductEntity;


public interface ProductRepository extends JpaRepository<ProductEntity,Long> {

    Page<ProductEntity> findAllByCategoryEntity_CategoryName (String CategoryName, Pageable pageable);
}
