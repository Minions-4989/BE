package shopping.main.millions.product;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@Table(name = "product")
@Entity
public class ProductEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_price")
    private Integer productPrice;

    @Column(name = "product_date")
    private LocalDateTime ProductDate;

    @Builder
    public ProductEntity(Long productId, String productName, Integer productPrice, LocalDateTime productDate) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.ProductDate = productDate;
    }
}
