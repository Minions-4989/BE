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
    private Long cartProductId;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;
}
