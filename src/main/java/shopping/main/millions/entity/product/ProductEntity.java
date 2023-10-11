package shopping.main.millions.entity.product;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicUpdate;
import org.joda.time.DateTime;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.joda.time.DateTime;
import shopping.main.millions.entity.cart.CartProductEntity;
import shopping.main.millions.entity.category.CategoryEntity;
import shopping.main.millions.entity.member.MemberEntity;

import shopping.main.millions.entity.order.OrderEntity;
import shopping.main.millions.repository.sales.GoodsEditRepository;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@AllArgsConstructor
@SuperBuilder
@DynamicUpdate
@Table(name = "product")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "product_name" , length = 30)
    private String productName; // 상품이름

    @Column(name = "product_price" , length = 10)
    private Integer productPrice; //상품가격


    @OneToMany(mappedBy = "productEntity", cascade = CascadeType.ALL)
    private List<CartProductEntity> cartProductEntityList;

    @OneToMany(mappedBy = "productEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<GoodsStockEntity> goodsStockEntityList;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity categoryEntity; // 카테고리


    @OneToMany(mappedBy = "productEntity", cascade = CascadeType.ALL)
    private List<GoodsImageEntity> goodsImageEntityList;
    // May To One 으로 재고테이블 연관관계

    @ManyToOne
    @JoinColumn(name="user_id")
    private MemberEntity memberEntity; //나의 판매물품 조회를 위한 유저 연관관계


    @OneToMany(mappedBy = "productEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderEntity> orderEntityList;




//    @Builder
//    public ProductEntity(String productName, Integer productPrice, List<CartProductEntity> cartProductEntityList, List<GoodsStockEntity> goodsStockEntityList, CategoryEntity categoryEntity, List<GoodsImageEntity> goodsImageEntity) {
//        this.productName = productName;
//        this.productPrice = productPrice;
//        this.cartProductEntityList = cartProductEntityList;
//        this.goodsStockEntityList = goodsStockEntityList;
//        this.categoryEntity = categoryEntity;
//        this.goodsImageEntity = goodsImageEntity;
//    }
}