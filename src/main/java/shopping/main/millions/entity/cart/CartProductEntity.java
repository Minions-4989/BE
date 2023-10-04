package shopping.main.millions.entity.cart;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shopping.main.millions.entity.product.ProductEntity;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor
@Table(name = "cart_product")
public class CartProductEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_product_id")
    private Long cartProductId;

    @Column(name = "cart_product_count")
    private Long cartProductCount;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity productEntity;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private CartEntity cartEntity;
}
