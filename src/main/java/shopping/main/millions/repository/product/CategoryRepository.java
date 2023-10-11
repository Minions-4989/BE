package shopping.main.millions.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;
import shopping.main.millions.entity.category.CategoryEntity;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    Optional<CategoryEntity> findByCategoryName(String categoryName);
}