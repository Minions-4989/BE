package shopping.main.millions.repository.sales;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import shopping.main.millions.entity.member.MemberEntity;
import shopping.main.millions.entity.product.GoodsImageEntity;
import shopping.main.millions.entity.product.GoodsStockEntity;
import shopping.main.millions.entity.product.ProductEntity;

import java.util.List;

public interface GoodsEditRepository extends JpaRepository<ProductEntity,Long > {


//        @EntityGraph(attributePaths = { "goodsStockEntityList", "goodsImageEntity" })
//        @Query("SELECT p FROM ProductEntity p WHERE p.productId = :productId")
//        List<ProductEntity> findByProductId(Long userId);

        @Query("SELECT p FROM ProductEntity p WHERE p.memberEntity.userId = :userId")
        List<ProductEntity> findProductsByUserId(@Param("userId") Long userId);

        List<MemberEntity> findMemberEntityAndMemberEntity_UserId(Long userId);
}

