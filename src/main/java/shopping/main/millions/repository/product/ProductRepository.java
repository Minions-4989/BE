package shopping.main.millions.repository.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import shopping.main.millions.entity.category.CategoryEntity;
import shopping.main.millions.entity.product.ProductEntity;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity,Long> {

    List<ProductEntity > findByMemberEntity_UserId(Long id);


    Page<ProductEntity> findAllByCategoryEntity_CategoryName (String categoryName, Pageable pageable);

}
