package shopping.main.millions.entity.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.joda.time.DateTime;
import shopping.main.millions.entity.cart.CartProductEntity;
import shopping.main.millions.entity.category.CategoryEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@Entity
@AllArgsConstructor
@SuperBuilder // 나중에 알아보기
// @Builder 는 상속받은 필드는 사용못함
// @SuperBuilder 는 그걸 보완하기위해서 나온것 근데 설명보니 자식 클래스하고 부모클래스 양쪽에 해야된다는데
// 제가 궁금한게 상속이면 extends인데 전 extends를 repository에서 밖에 안썻는데
@Table(name = "product")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "product_name")
    private String productName; // 상품이름

    @Column(name = "product_price")
    private String productPrice;

    @Column(name = "product_date")
    private Date productDate; //판매가능날짜

    @OneToMany(mappedBy = "productEntity", cascade = CascadeType.ALL)
    private List<CartProductEntity> cartProductEntityList;

    @OneToMany(mappedBy = "productEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<GoodsStockEntity> goodsStockEntityList;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity categoryEntity; // 카테고리


    @OneToMany(mappedBy = "productEntity", cascade = CascadeType.ALL)
    private List<GoodsImageEntity> goodsImageEntity;
    // May To One 으로 재고테이블 연관관계

}
