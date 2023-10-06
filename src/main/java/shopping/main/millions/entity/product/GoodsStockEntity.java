package shopping.main.millions.entity.product;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.*;

import shopping.main.millions.entity.product.ProductEntity;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "stock")
@Getter @Setter
@SuperBuilder
//재고 테이블
public class GoodsStockEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_id")
    private Long stockId;       //stock

    @Column(name = "stock_quantity", nullable = false)
    private Long stockQuantity; //수량

    @Column(name = "stock_size", nullable = false, length = 10)
    private String stockSize; // 사이즈

    @Column(name = "stock_color", nullable = false)
    private String stockColor; // 색상

    // Many To One 으로 product랑 연관관계
    //연관
    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity productEntity;
}
