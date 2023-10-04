package shopping.main.millions.entity.product;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shopping.main.millions.entity.cart.CartProductEntity;
import shopping.main.millions.entity.category.CategoryEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@Entity
@Table(name = "product")
public class ProductEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_price")
    private String productPrice;

    @Column(name = "product_date")
    private LocalDateTime ProductDate;

    @OneToMany(mappedBy = "productEntity", cascade = CascadeType.ALL)
    private List<CartProductEntity> cartProductEntityList;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity categoryEntity;

    @Builder
    public ProductEntity(Long productId, String productName, String productPrice, LocalDateTime productDate) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.ProductDate = productDate;
    }
}
