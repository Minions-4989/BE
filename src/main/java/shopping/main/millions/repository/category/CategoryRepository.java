package shopping.main.millions.repository.category;

import org.springframework.data.jpa.repository.JpaRepository;
import shopping.main.millions.entity.category.CategoryEntity;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryEntity,Long> {
    Optional<CategoryEntity> findCategoryEntityByCategoryName(String categoryName);
}