package shopping.main.millions.entity.product;



import lombok.experimental.SuperBuilder;
import lombok.*;

import shopping.main.millions.entity.product.ProductEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "stock")
@Getter
@Setter
@SuperBuilder
//재고 테이블
public class GoodsStockEntity {

    @Id
    @GeneratedValue
    @Column(name = "stock_id")
    private Long stockId;       //stock

    @Column(name = "stock_quantity", nullable = false , length = 10)
    private Long stockQuantity; //수량

    @Column(name = "stock_size", nullable = false, length = 10)
    private String stockSize; // 사이즈

    @Column(name = "stock_color", nullable = false, length = 20)
    private String stockColor; // 색상

    // Many To One 으로 product랑 연관관계
    //연관
    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity productEntity;

    @Transient
    private MultipartFile stockFile;

    @Builder
    public GoodsStockEntity(Long stockQuantity, String stockSize, String stockColor) {
        this.stockQuantity = stockQuantity;
        this.stockSize = stockSize;
        this.stockColor = stockColor;
    }
}
