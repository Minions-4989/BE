package shopping.main.millions.entity.cart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import shopping.main.millions.entity.product.ProductEntity;
import shopping.main.millions.entity.cart.CartEntity;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cart_product")
@SuperBuilder
public class CartProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_product_id")
    private Long cartProductId;

    @Column(name = "cart_product_count")
    private Long cartProductCount; //구매할 개수

    @Column(name = "cart_product_size")
    private String cartProductSize; // 구매할 상품 사이즈

    @Column(name = "cart_product_color")
    private String cartProductColor; //구매할 상품 컬러

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity productEntity;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private CartEntity cartEntity;
}